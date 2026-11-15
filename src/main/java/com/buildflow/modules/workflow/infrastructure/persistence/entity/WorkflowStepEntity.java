package com.buildflow.modules.workflow.infrastructure.persistence.entity;

import com.buildflow.modules.workflow.domain.model.TaskType;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import tools.jackson.databind.JsonNode;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "workflow_steps")
public class WorkflowStepEntity{

    protected WorkflowStepEntity() {}

    public WorkflowStepEntity(WorkflowEntity workflow,
                              String name,
                              int stepOrder,
                              TaskType taskType,
                              int maxRetries,
                              int timeoutSeconds,
                              JsonNode configuration) {
        this.workflow = Objects.requireNonNull(workflow);
        this.name = name;
        this.stepOrder = stepOrder;
        this.taskType = taskType;
        this.maxRetries = maxRetries;
        this.timeoutSeconds = timeoutSeconds;
        this.configuration = Objects.requireNonNull(configuration);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id", nullable = false)
    private WorkflowEntity workflow;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "step_order", nullable = false)
    private int stepOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_type",nullable = false, length = 30)
    private TaskType taskType;

    @Column(name = "max_retries",nullable = false)
    private int maxRetries;

    @Column(name = "timeout_seconds",nullable = false)
    private int timeoutSeconds;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "configuration",nullable = false)
    private JsonNode configuration;

    public void setWorkflow(WorkflowEntity workflow) {
        this.workflow = Objects.requireNonNull(workflow);
    }

    public WorkflowEntity getWorkflow() {
        return workflow;
    }

    public UUID getId(){
        return id;
    }

    public JsonNode getConfiguration() {
        return configuration;
    }
}