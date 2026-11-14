package com.buildflow.modules.workflow.infrastructure.persistence.entity;

import com.buildflow.modules.workflow.domain.model.ExecutionStatus;
import com.buildflow.modules.workflow.domain.model.TaskType;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "task_executions")
public class TaskExecutionEntity {
    protected TaskExecutionEntity() {

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
}