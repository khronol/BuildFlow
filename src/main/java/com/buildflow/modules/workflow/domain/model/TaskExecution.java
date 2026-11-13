
package com.buildflow.modules.workflow.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class TaskExecution {

    private final UUID id;
    private final UUID executionId;

    private final String stepName;
    private final int stepOrder;

    private final TaskType taskType;

    private ExecutionStatus status;
    private int attemptCount;

    private String errorMessage;

    private Instant startedAt;
    private Instant completedAt;

    public TaskExecution(
            UUID id,
            UUID executionId,
            String stepName,
            int stepOrder,
            TaskType taskType
    ) {
        this.id = Objects.requireNonNull(id);
        this.executionId = Objects.requireNonNull(executionId);
        this.stepName = Objects.requireNonNull(stepName);
        this.taskType = Objects.requireNonNull(taskType);

        if (stepName.isBlank()) {
            throw new IllegalArgumentException(
                    "Step name cannot be blank"
            );
        }

        if (stepOrder < 1) {
            throw new IllegalArgumentException(
                    "Step order must be greater than zero"
            );
        }

        this.stepOrder = stepOrder;
        this.status = ExecutionStatus.PENDING;
        this.attemptCount = 0;
    }

    public void startAttempt(Instant now) {
        if (status != ExecutionStatus.PENDING
                && status != ExecutionStatus.FAILED) {
            throw new IllegalStateException(
                    "Task cannot be started"
            );
        }

        status = ExecutionStatus.RUNNING;
        attemptCount++;

        startedAt = Objects.requireNonNull(now);
        completedAt = null;
        errorMessage = null;
    }

    public void complete(Instant now) {
        if (status != ExecutionStatus.RUNNING) {
            throw new IllegalStateException(
                    "Only running task can be completed"
            );
        }

        status = ExecutionStatus.COMPLETED;
        completedAt = Objects.requireNonNull(now);
    }

    public void fail(String errorMessage, Instant now) {
        if (status != ExecutionStatus.RUNNING) {
            throw new IllegalStateException(
                    "Only running task can fail"
            );
        }

        status = ExecutionStatus.FAILED;
        this.errorMessage = Objects.requireNonNull(errorMessage);
        completedAt = Objects.requireNonNull(now);
    }

    public UUID getId() {
        return id;
    }

    public UUID getExecutionId() {
        return executionId;
    }

    public String getStepName() {
        return stepName;
    }

    public int getStepOrder() {
        return stepOrder;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public ExecutionStatus getStatus() {
        return status;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }
}
