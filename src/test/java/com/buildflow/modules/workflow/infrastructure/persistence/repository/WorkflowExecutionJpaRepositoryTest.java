package com.buildflow.modules.workflow.infrastructure.persistence.repository;

import com.buildflow.modules.workflow.domain.model.ExecutionStatus;
import com.buildflow.modules.workflow.domain.model.WorkflowStatus;
import com.buildflow.modules.workflow.infrastructure.persistence.entity.WorkflowEntity;
import com.buildflow.modules.workflow.infrastructure.persistence.entity.WorkflowExecutionEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WorkflowExecutionJpaRepositoryTest {

    @Autowired
    private WorkflowJpaRepository workflowRepository;

    @Autowired
    private WorkflowExecutionJpaRepository executionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void shouldSaveWorkflowExecution(){
        WorkflowEntity workflow = new WorkflowEntity("test","test", WorkflowStatus.ACTIVE, Instant.now(),Instant.now());
        workflowRepository.saveAndFlush(workflow);

        WorkflowExecutionEntity execution = new WorkflowExecutionEntity(workflow, ExecutionStatus.PENDING,Instant.now());
        executionRepository.saveAndFlush(execution);

        assertNotNull(workflow.getId());

        entityManager.clear();

        WorkflowExecutionEntity found = executionRepository.findById(execution.getId()).orElseThrow();

        assertEquals(ExecutionStatus.PENDING, found.getStatus());

        assertEquals(workflow.getId(), found.getWorkflow().getId());


    }
}