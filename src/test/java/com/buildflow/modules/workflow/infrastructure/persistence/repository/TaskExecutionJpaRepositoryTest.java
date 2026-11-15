import com.buildflow.modules.workflow.domain.model.ExecutionStatus;
import com.buildflow.modules.workflow.domain.model.TaskType;
import com.buildflow.modules.workflow.domain.model.WorkflowStatus;
import com.buildflow.modules.workflow.infrastructure.persistence.entity.TaskExecutionEntity;
import com.buildflow.modules.workflow.infrastructure.persistence.entity.WorkflowEntity;
import com.buildflow.modules.workflow.infrastructure.persistence.entity.WorkflowExecutionEntity;
import com.buildflow.modules.workflow.infrastructure.persistence.repository.TaskExecutionJpaRepository;
import com.buildflow.modules.workflow.infrastructure.persistence.repository.WorkflowExecutionJpaRepository;
import com.buildflow.modules.workflow.infrastructure.persistence.repository.WorkflowJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.time.Instant;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaskExecutionJpaRepositoryTest{
    @Autowired
    private WorkflowJpaRepository workflowRepository;

    @Autowired
    private WorkflowExecutionJpaRepository executionRepository;

    @Autowired
    private TaskExecutionJpaRepository taskExecutionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void shouldSaveTaskExecution(){
        WorkflowEntity workflow = new WorkflowEntity("test","test", WorkflowStatus.ACTIVE, Instant.now(),Instant.now());
        workflowRepository.saveAndFlush(workflow);

        WorkflowExecutionEntity execution = new WorkflowExecutionEntity(workflow, ExecutionStatus.PENDING,Instant.now());
        executionRepository.saveAndFlush(execution);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode configuration = mapper.createObjectNode();
        configuration.put("url", "https://example.com");
        configuration.put("method", "GET");

        TaskExecutionEntity task = new TaskExecutionEntity(execution,"https://test.com",1, TaskType.HTTP,3,30,configuration);
        taskExecutionRepository.saveAndFlush(task);
    }
}