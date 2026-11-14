package com.buildflow.modules.workflow.infrastructure.persistence.entity;

import com.buildflow.modules.workflow.domain.model.ExecutionStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "workflow_executions")
public class WorkflowExecutionEntity {
    protected WorkflowExecutionEntity(){

    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id", nullable = false)
    private WorkflowEntity workflow;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false, length = 20)
    private ExecutionStatus status;

    @Column(name = "created_at",nullable = false)
    private Instant createdAt;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    @Column(name = "error_message")
    private String errorMessage;

    @OneToMany(mappedBy = "workflowExecution", fetch = FetchType.LAZY)
    private List<TaskExecutionEntity> taskExecutions = new ArrayList<>();
}
