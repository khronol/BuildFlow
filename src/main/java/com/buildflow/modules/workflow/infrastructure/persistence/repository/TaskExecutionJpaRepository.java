package com.buildflow.modules.workflow.infrastructure.persistence.repository;

import com.buildflow.modules.workflow.infrastructure.persistence.entity.TaskExecutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskExecutionJpaRepository
        extends JpaRepository<TaskExecutionEntity, UUID> {

}