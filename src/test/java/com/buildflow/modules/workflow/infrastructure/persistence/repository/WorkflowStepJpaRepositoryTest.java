package com.buildflow.modules.workflow.infrastructure.persistence.repository;

import com.buildflow.modules.workflow.domain.model.TaskType;
import com.buildflow.modules.workflow.domain.model.WorkflowStatus;
import com.buildflow.modules.workflow.infrastructure.persistence.entity.WorkflowEntity;
import com.buildflow.modules.workflow.infrastructure.persistence.entity.WorkflowStepEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WorkflowStepJpaRepositoryTest {

    @Autowired
    private WorkflowJpaRepository repositoryWorkflow;

    @Autowired
    private WorkflowStepJpaRepository repositoryStep;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void shouldSaveWorkflowStep() {

        WorkflowEntity workflow = new WorkflowEntity("test","test", WorkflowStatus.ACTIVE, Instant.now(),Instant.now());
        repositoryWorkflow.saveAndFlush(workflow);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("test","test");
        node.put("anotherTest","anotherTest");

        WorkflowStepEntity step = new WorkflowStepEntity(workflow,"test",1, TaskType.HTTP,2,3,node);

        workflow.addStep(step);

        WorkflowStepEntity savedStep = repositoryStep.saveAndFlush(step);

        assertEquals(1, workflow.getSteps().size());

        assertNotNull(savedStep.getId());

        assertEquals(workflow.getId(), savedStep.getWorkflow().getId());

        entityManager.clear();

        WorkflowEntity reloadedWorkflow = repositoryWorkflow.findById(workflow.getId()).orElseThrow();

        assertEquals(1, reloadedWorkflow.getSteps().size());

        WorkflowStepEntity reloadedStep = repositoryStep.findById(savedStep.getId()).orElseThrow();

        assertEquals(node, reloadedStep.getConfiguration());
    }

}