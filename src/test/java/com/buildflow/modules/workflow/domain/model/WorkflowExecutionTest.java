
package com.buildflow.modules.workflow.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WorkflowExecutionTest {

    private WorkflowExecution execution;

    private final UUID workflowId = UUID.randomUUID();
    private final Instant now = Instant.parse("2026-11-13T10:00:00Z");

    @BeforeEach
    void setUp() {
        execution = new WorkflowExecution(
                UUID.randomUUID(),
                workflowId,
                now
        );
    }

    @Test
    @DisplayName("New execution should have PENDING status")
    void shouldHavePendingStatusWhenCreated() {
        assertEquals(
                ExecutionStatus.PENDING,
                execution.getStatus()
        );
    }

    @Test
    @DisplayName("Should transition from PENDING to RUNNING")
    void shouldStartExecution() {
        execution.start(now);

        assertEquals(
                ExecutionStatus.RUNNING,
                execution.getStatus()
        );

        assertEquals(now, execution.getStartedAt());
    }

    @Test
    @DisplayName("Should complete running execution")
    void shouldCompleteExecution() {
        execution.start(now);
        execution.complete(now.plusSeconds(10));

        assertEquals(
                ExecutionStatus.COMPLETED,
                execution.getStatus()
        );

        assertEquals(
                now.plusSeconds(10),
                execution.getCompletedAt()
        );
    }

    @Test
    @DisplayName("Should fail running execution")
    void shouldFailExecution() {
        execution.start(now);
        execution.fail("Payment failed", now.plusSeconds(10));

        assertEquals(
                ExecutionStatus.FAILED,
                execution.getStatus()
        );

        assertEquals(
                "Payment failed",
                execution.getErrorMessage()
        );
    }

    @Test
    @DisplayName("Should cancel pending execution")
    void shouldCancelPendingExecution() {
        execution.cancel(now);

        assertEquals(
                ExecutionStatus.CANCELLED,
                execution.getStatus()
        );
    }

    @Test
    @DisplayName("Should not complete pending execution")
    void shouldNotCompletePendingExecution() {
        assertThrows(
                IllegalStateException.class,
                () -> execution.complete(now)
        );

        assertEquals(
                ExecutionStatus.PENDING,
                execution.getStatus()
        );
    }

    @Test
    @DisplayName("Should not start completed execution")
    void shouldNotRestartCompletedExecution() {
        execution.start(now);
        execution.complete(now.plusSeconds(10));

        assertThrows(
                IllegalStateException.class,
                () -> execution.start(now.plusSeconds(20))
        );

        assertEquals(
                ExecutionStatus.COMPLETED,
                execution.getStatus()
        );
    }

    @Test
    @DisplayName("Null timestamp should not change execution state")
    void shouldNotChangeStateWhenTimestampIsNull() {
        execution.start(now);

        assertThrows(
                NullPointerException.class,
                () -> execution.complete(null)
        );

        assertEquals(
                ExecutionStatus.RUNNING,
                execution.getStatus()
        );

        assertNull(execution.getCompletedAt());
    }
}
