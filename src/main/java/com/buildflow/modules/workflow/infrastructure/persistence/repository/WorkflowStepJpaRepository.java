package com.buildflow.modules.workflow.infrastructure.persistence.repository;

import com.buildflow.modules.workflow.infrastructure.persistence.entity.WorkflowStepEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkflowStepJpaRepository extends JpaRepository<WorkflowStepEntity, UUID> {

}