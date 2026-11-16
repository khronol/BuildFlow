
package com.buildflow.modules.workflow.domain.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Workflow {

    private final UUID id;
    private final String name;
    private final String description;

    private WorkflowStatus status;

    private final List<WorkflowStep> steps;

    private final Instant createdAt;
    private Instant updatedAt;

    public Workflow(
            UUID id,
            String name,
            String description,
            WorkflowStatus status,
            Instant createdAt,
            Instant updatedAt,
            List<WorkflowStep> steps
    ){

        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        if(name.isBlank()){
            throw new IllegalArgumentException(
                    "Workflow name cannot be blank"
            );
        }
        this.description = description;
        this.status = Objects.requireNonNull(status);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
        this.steps = new ArrayList<>(Objects.requireNonNull(steps));
    }

    public Workflow(
            UUID id,
            String name,
            String description,
            WorkflowStatus status,
            Instant createdAt,
            Instant updatedAt
    ){

       this.id = Objects.requireNonNull(id);
       this.name = Objects.requireNonNull(name);
        if(name.isBlank()){
            throw new IllegalArgumentException(
                    "Workflow name cannot be blank"
            );
        }
       this.description = description;
       this.status = Objects.requireNonNull(status);
       this.createdAt = Objects.requireNonNull(createdAt);
       this.updatedAt = Objects.requireNonNull(updatedAt);
       this.steps = new ArrayList<>();
    }

    public Workflow(
            UUID id,
            String name,
            String description,
            Instant createdAt
    ) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.description = description;

        if (name.isBlank()) {
            throw new IllegalArgumentException(
                    "Workflow name cannot be blank"
            );
        }

        this.status = WorkflowStatus.INACTIVE;
        this.steps = new ArrayList<>();

        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = createdAt;
    }

    public void activate(Instant now) {
        Objects.requireNonNull(now);
        this.status = WorkflowStatus.ACTIVE;
        this.updatedAt = Objects.requireNonNull(now);
    }

    public void deactivate(Instant now) {
        Objects.requireNonNull(now);
        this.status = WorkflowStatus.INACTIVE;
        this.updatedAt = Objects.requireNonNull(now);
    }

    public void addStep(WorkflowStep step, Instant now) {
        Objects.requireNonNull(step);

        if (!id.equals(step.getWorkflowId())) {
            throw new IllegalArgumentException(
                    "Step belongs to another workflow"
            );
        }

        boolean orderExists = steps.stream()
                .anyMatch(s -> s.getStepOrder() == step.getStepOrder());

        if (orderExists) {
            throw new IllegalArgumentException(
                    "Step order already exists"
            );
        }

        steps.add(step);
        updatedAt = Objects.requireNonNull(now);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public WorkflowStatus getStatus() {
        return status;
    }

    public List<WorkflowStep> getSteps() {
        return List.copyOf(steps);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
