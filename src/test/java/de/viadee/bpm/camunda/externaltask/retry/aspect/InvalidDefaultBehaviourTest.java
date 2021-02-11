package de.viadee.bpm.camunda.externaltask.retry.aspect;

import org.junit.Test;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.Mockito.when;

@TestPropertySource(properties = "de.viadee.bpm.camunda.external-task.default-retry-time-cycle=R3/PT1D")
public class InvalidDefaultBehaviourTest extends BaseTest {


    @Test
    public void runtimeException() {
        // prepare
        when(this.externalTask.getRetries()).thenReturn(null);

        // test
        this.externalTaskRetryAspect.handleErrorAfterThrown(this.joinPoint, new RuntimeException(), this.externalTask, this.externalTaskService);

        // verify
        this.verifyNoBpmnErrorAtAll();
        this.verifyHandleFailure();

        // assert
        this.assertRemainingRetries(3);
        this.assertNextRetryInterval(5 * MINUTES_TO_MILLIS);
    }


}
