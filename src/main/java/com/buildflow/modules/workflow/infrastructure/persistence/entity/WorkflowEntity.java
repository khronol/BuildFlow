package com.buildflow.modules.workflow.infrastructure.persistence.entity;

import com.buildflow.modules.workflow.domain.model.WorkflowStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "workflows")
public class WorkflowEntity{

    protected  WorkflowEntity() {}
    public WorkflowEntity(String name, String description, WorkflowStatus status, Instant createdAt, Instant updatedAt) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false, length = 20)
    private WorkflowStatus status;

    @Column(name = "created_at",nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at",nullable = false)
    private Instant updatedAt;

    @OrderBy("stepOrder ASC")
    @OneToMany(mappedBy = "workflow", fetch = FetchType.LAZY)
    private List<WorkflowStepEntity> steps = new ArrayList<>();

    @OneToMany(mappedBy = "workflow", fetch = FetchType.LAZY)
    private List<WorkflowExecutionEntity> executions = new ArrayList<>();

    public UUID getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public List<WorkflowStepEntity> getSteps(){
        return steps;
    }

    public void addStep(WorkflowStepEntity step) {
        steps.add(step);
        step.setWorkflow(this);
    }
}
