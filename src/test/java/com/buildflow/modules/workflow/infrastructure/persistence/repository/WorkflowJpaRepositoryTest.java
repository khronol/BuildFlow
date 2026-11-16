package com.buildflow.modules.workflow.infrastructure.persistence.repository;

import com.buildflow.modules.workflow.domain.model.WorkflowStatus;
import com.buildflow.modules.workflow.infrastructure.persistence.entity.WorkflowEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.Instant;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WorkflowJpaRepositoryTest {

    @Autowired
    private WorkflowJpaRepository repository;

    @Test
    void addNewEntity() {
        WorkflowEntity workflow = new WorkflowEntity("test","test", WorkflowStatus.ACTIVE, Instant.now(),Instant.now());
        WorkflowEntity saved = repository.saveAndFlush(workflow);

        Optional<WorkflowEntity> found = repository.findById(saved.getId());

        assertTrue(found.isPresent());

        assertEquals("test", found.orElseThrow().getName());

        assertNotNull(saved.getId());

    }
}