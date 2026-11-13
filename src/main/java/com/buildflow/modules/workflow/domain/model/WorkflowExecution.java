
package com.buildflow.modules.workflow.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class WorkflowExecution {

    private final UUID id;
    private final UUID workflowId;

    private ExecutionStatus status;

    private final Instant createdAt;
    private Instant startedAt;
    private Instant completedAt;

    private String errorMessage;

    public WorkflowExecution(
            UUID id,
            UUID workflowId,
            Instant createdAt
    ) {
        this.id = Objects.requireNonNull(id);
        this.workflowId = Objects.requireNonNull(workflowId);
        this.createdAt = Objects.requireNonNull(createdAt);

        this.status = ExecutionStatus.PENDING;
    }

    public void start(Instant now) {
        if (status != ExecutionStatus.PENDING) {
            throw new IllegalStateException(
                    "Only pending execution can be started"
            );
        }

        status = ExecutionStatus.RUNNING;
        startedAt = Objects.requireNonNull(now);
    }

    public void complete(Instant now) {
        if (status != ExecutionStatus.RUNNING) {
            throw new IllegalStateException(
                    "Only running execution can be completed"
            );
        }

        Objects.requireNonNull(now);

        status = ExecutionStatus.COMPLETED;
        completedAt = now;
    }

    public void fail(String errorMessage, Instant now) {
        if (status != ExecutionStatus.RUNNING) {
            throw new IllegalStateException(
                    "Only running execution can fail"
            );
        }

        status = ExecutionStatus.FAILED;
        this.errorMessage = Objects.requireNonNull(errorMessage);
        completedAt = Objects.requireNonNull(now);
    }

    public void cancel(Instant now) {
        if (status != ExecutionStatus.PENDING
                && status != ExecutionStatus.RUNNING) {
            throw new IllegalStateException(
                    "Execution cannot be cancelled"
            );
        }

        status = ExecutionStatus.CANCELLED;
        completedAt = Objects.requireNonNull(now);
    }

    public UUID getId() {
        return id;
    }

    public UUID getWorkflowId() {
        return workflowId;
    }

    public ExecutionStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
