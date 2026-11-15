package com.buildflow.modules.workflow.infrastructure.persistence.entity;

import com.buildflow.modules.workflow.domain.model.ExecutionStatus;
import com.buildflow.modules.workflow.domain.model.TaskType;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import tools.jackson.databind.JsonNode;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "task_executions")
public class TaskExecutionEntity {
    protected TaskExecutionEntity() {

    }

    public TaskExecutionEntity(WorkflowExecutionEntity execution,
                               String stepName,
                               int stepOrder,
                               TaskType taskType,
                               int maxRetries,
                               int timeoutSeconds,
                               JsonNode configuration){
        if (stepOrder <= 0) {
            throw new IllegalArgumentException("stepOrder must be positive");
        }
        if (maxRetries < 0) {
            throw new IllegalArgumentException("maxRetries cannot be negative");
        }
        if (timeoutSeconds <= 0) {
            throw new IllegalArgumentException("timeoutSeconds must be positive");
        }

        this.workflowExecution = Objects.requireNonNull(execution);
        this.stepName = Objects.requireNonNull(stepName);
        this.stepOrder = stepOrder;
        this.status = ExecutionStatus.PENDING;
        this.taskType = Objects.requireNonNull(taskType);
        this.attemptCount = 0;
        this.errorMessage = null;
        this.startedAt = null;
        this.completedAt = null;
        this.maxRetries = maxRetries;
        this.timeoutSeconds = timeoutSeconds;
        this.configuration = Objects.requireNonNull(configuration).deepCopy();
        this.input = null;
        this.output = null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "execution_id", nullable = false)
    private WorkflowExecutionEntity workflowExecution;

    @Column(name = "step_name", nullable = false)
    private String stepName;

    @Column(name = "step_order", nullable = false)
    private int stepOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false, length = 20)
    private ExecutionStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_type",nullable = false, length = 30)
    private TaskType taskType;

    @Column(name = "attempt_count",nullable = false)
    private int attemptCount;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    @Column(name = "max_retries",nullable = false)
    private int maxRetries;

    @Column(name = "timeout_seconds",nullable = false)
    private int timeoutSeconds;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "configuration", nullable = false)
    private JsonNode configuration;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "input")
    private JsonNode input;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "output")
    private JsonNode output;

    public UUID getId() {
        return id;
    }

    public WorkflowExecutionEntity getWorkflowExecution() {
        return workflowExecution;
    }

    public String getStepName() {
        return stepName;
    }

    public ExecutionStatus getStatus() {
        return status;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public JsonNode getConfiguration() {
        return configuration;
    }
}