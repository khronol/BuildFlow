CREATE TABLE workflows
(
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name VARCHAR NOT NULL CHECK (LENGTH(TRIM(name)) > 0),
  description TEXT,
  status VARCHAR(20) NOT NULL CHECK ( status in('ACTIVE','INACTIVE') ),
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE workflow_steps
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    workflow_id UUID NOT NULL REFERENCES workflows(id) ON DELETE RESTRICT,
    name VARCHAR NOT NULL CHECK (LENGTH(TRIM(name)) > 0),
    step_order INTEGER NOT NULL CHECK ( step_order > 0 ),
    task_type VARCHAR(30) NOT NULL CHECK (task_type IN ('HTTP')),
    max_retries INTEGER NOT NULL CHECK ( max_retries >= 0 ),
    timeout_seconds INTEGER NOT NULL CHECK ( timeout_seconds > 0 ),
    configuration JSONB NOT NULL,
    UNIQUE(workflow_id, step_order)
);

CREATE TABLE workflow_executions
(
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  workflow_id UUID NOT NULL REFERENCES workflows(id) ON DELETE RESTRICT,
  status VARCHAR(20) NOT NULL CHECK ( status in('PENDING','RUNNING','COMPLETED','FAILED','CANCELLED')),
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  started_at TIMESTAMPTZ,
  completed_at TIMESTAMPTZ,
  error_message TEXT
);

CREATE TABLE task_executions
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    execution_id UUID NOT NULL REFERENCES workflow_executions(id) ON DELETE RESTRICT,
    step_name TEXT NOT NULL,
    step_order INT NOT NULL CHECK ( step_order > 0 ),
    status VARCHAR(20) NOT NULL CHECK ( status in('PENDING','RUNNING','COMPLETED','FAILED','CANCELLED')),
    task_type VARCHAR(30) NOT NULL CHECK (task_type IN ('HTTP')),
    attempt_count INT NOT NULL CHECK (attempt_count >= 0) DEFAULT 0,
    configuration JSONB NOT NULL,
    input JSONB,
    output JSONB,
    error_message TEXT,
    started_at TIMESTAMPTZ,
    completed_at TIMESTAMPTZ,
    max_retries INTEGER NOT NULL CHECK (max_retries >= 0),
    timeout_seconds INTEGER NOT NULL CHECK (timeout_seconds > 0),
    UNIQUE (execution_id, step_order)
);

CREATE INDEX idx_workflow_executions_workflow_id
    ON workflow_executions(workflow_id);

CREATE INDEX idx_workflow_executions_status
    ON workflow_executions(status);

CREATE INDEX idx_task_executions_status
    ON task_executions(status);