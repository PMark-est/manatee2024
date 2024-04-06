package ee.cyber.manatee.statemachine;


import org.springframework.statemachine.StateMachine;

import javax.swing.plaf.nimbus.State;


public interface ApplicationStateMachine {

    StateMachine<ApplicationState, ApplicationEvent> rejectApplication(Integer applicationId);

    StateMachine<ApplicationState, ApplicationEvent> scheduleInterview(Integer applicationId);
}
