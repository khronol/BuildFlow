
package com.buildflow.modules.workflow.domain.model;

import java.util.Objects;
import java.util.UUID;

public class WorkflowStep {

    private final UUID id;
    private final UUID workflowId;

    private final String name;
    private final int stepOrder;

    private final TaskType taskType;

    private final int maxRetries;
    private final int timeoutSeconds;

    public WorkflowStep(
            UUID id,
            UUID workflowId,
            String name,
            int stepOrder,
            TaskType taskType,
            int maxRetries,
            int timeoutSeconds
    ) {
        this.id = Objects.requireNonNull(id);
        this.workflowId = Objects.requireNonNull(workflowId);
        this.name = Objects.requireNonNull(name);
        this.taskType = Objects.requireNonNull(taskType);

        if (name.isBlank()) {
            throw new IllegalArgumentException(
                    "Step name cannot be blank"
            );
        }

        if (stepOrder < 1) {
            throw new IllegalArgumentException(
                    "Step order must be greater than zero"
            );
        }

        if (maxRetries < 0) {
            throw new IllegalArgumentException(
                    "Max retries cannot be negative"
            );
        }

        if (timeoutSeconds <= 0) {
            throw new IllegalArgumentException(
                    "Timeout must be greater than zero"
            );
        }

        this.stepOrder = stepOrder;
        this.maxRetries = maxRetries;
        this.timeoutSeconds = timeoutSeconds;
    }

    public UUID getId() {
        return id;
    }

    public UUID getWorkflowId() {
        return workflowId;
    }

    public String getName() {
        return name;
    }

    public int getStepOrder() {
        return stepOrder;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }
}
