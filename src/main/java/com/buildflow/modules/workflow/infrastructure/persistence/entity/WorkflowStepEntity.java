package com.buildflow.modules.workflow.infrastructure.persistence.entity;

import com.buildflow.modules.workflow.domain.model.TaskType;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "workflow_steps")
public class WorkflowStepEntity{
    protected WorkflowStepEntity() {

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


}