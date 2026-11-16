package com.buildflow.modules.workflow.infrastructure.persistence.mapper;

import com.buildflow.modules.workflow.domain.model.Workflow;
import com.buildflow.modules.workflow.domain.model.WorkflowStep;
import com.buildflow.modules.workflow.infrastructure.persistence.entity.WorkflowEntity;
import com.buildflow.modules.workflow.infrastructure.persistence.entity.WorkflowStepEntity;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class WorkflowMapper {
    public WorkflowStep toDomain(WorkflowStepEntity entity) {
        Objects.requireNonNull(entity);
        return new WorkflowStep(
                entity.getId(),
                entity.getWorkflow().getId(),
                entity.getName(),
                entity.getStepOrder(),
                entity.getTaskType(),
                entity.getMaxRetries(),
                entity.getTimeoutSeconds(),
                entity.getConfiguration()
        );
    }

    public WorkflowStepEntity toEntity(WorkflowStep domain, WorkflowEntity workflowEntity) {

        Objects.requireNonNull(domain);
        Objects.requireNonNull(workflowEntity);

        if (!domain.getWorkflowId().equals(workflowEntity.getId())) {
            throw new IllegalArgumentException(
                    "Workflow ID mismatch"
            );
        }

        return new WorkflowStepEntity(
                workflowEntity,
                domain.getName(),
                domain.getStepOrder(),
                domain.getTaskType(),
                domain.getMaxRetries(),
                domain.getTimeoutSeconds(),
                domain.getConfiguration());
    }

    public Workflow toDomain(WorkflowEntity entity){
        Objects.requireNonNull(entity);

        List<WorkflowStep> steps = entity.getSteps()
                .stream()
                .map(this::toDomain)
                .toList();


        return new Workflow(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                steps
        );
    }
}