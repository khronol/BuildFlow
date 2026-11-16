package com.buildflow.modules.workflow.infrastructure.persistence.mapper;

import com.buildflow.modules.workflow.domain.model.TaskType;
import com.buildflow.modules.workflow.domain.model.WorkflowStatus;
import com.buildflow.modules.workflow.domain.model.WorkflowStep;
import com.buildflow.modules.workflow.infrastructure.persistence.entity.WorkflowEntity;
import com.buildflow.modules.workflow.infrastructure.persistence.entity.WorkflowStepEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WorkflowMapperTest{
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void shouldMapWorkflowStepEntityToDomain() {
        WorkflowEntity workflowEntity = new WorkflowEntity(
                "test",
                "descriptionTest",
                WorkflowStatus.ACTIVE,
                Instant.now(),
                Instant.now());


        entityManager.persist(workflowEntity);
        entityManager.flush();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode configuration = mapper.createObjectNode();
        configuration.put("url", "https://example.com");
        configuration.put("method", "GET");

        WorkflowStepEntity stepEntity = new WorkflowStepEntity(
                workflowEntity,
                "stepTest",
                1,
                TaskType.HTTP,
                3,
                30,
                configuration
                );
        entityManager.persist(stepEntity);
        entityManager.flush();

        entityManager.clear();

        WorkflowStepEntity savedStep = entityManager.find(
                WorkflowStepEntity.class,
                stepEntity.getId()
        );


        WorkflowMapper workflowMapper = new WorkflowMapper();
        WorkflowStep result = workflowMapper.toDomain(savedStep);

        assertEquals(savedStep.getId(), result.getId());
        assertEquals(workflowEntity.getId(), result.getWorkflowId());
        assertEquals(savedStep.getName(),result.getName());
        assertEquals(savedStep.getStepOrder(), result.getStepOrder());
        assertEquals(savedStep.getMaxRetries(),result.getMaxRetries());
        assertEquals(savedStep.getTaskType(),result.getTaskType());
        assertEquals(savedStep.getTimeoutSeconds(),result.getTimeoutSeconds());
        assertEquals(configuration, result.getConfiguration());
    }
}