package ee.cyber.manatee.statemachine;


import org.springframework.statemachine.StateMachine;

public interface ApplicationStateMachine {

    StateMachine<ApplicationState, ApplicationEvent> rejectApplication(Integer applicationId);

    StateMachine<ApplicationState, ApplicationEvent> scheduleInterview(Integer applicationId);
}
