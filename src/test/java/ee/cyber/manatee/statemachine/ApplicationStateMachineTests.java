package ee.cyber.manatee.statemachine;


import ee.cyber.manatee.repository.InterviewRepository;
import ee.cyber.manatee.service.InterviewService;
import jakarta.transaction.Transactional;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ee.cyber.manatee.model.Application;
import ee.cyber.manatee.model.Candidate;
import ee.cyber.manatee.repository.ApplicationRepository;
import ee.cyber.manatee.service.ApplicationService;

import java.time.OffsetDateTime;

import static ee.cyber.manatee.statemachine.ApplicationState.*;
import static ee.cyber.manatee.statemachine.InterviewType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest
public class ApplicationStateMachineTests {

    @Autowired
    ApplicationService applicationService;

    @Autowired
    ApplicationStateMachine applicationStateMachine;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    InterviewService interviewService;

    @Autowired
    InterviewRepository interviewRepository;

    @Test
    @Transactional
    public void applicationGetsRejected() {
        val newCandidate = Candidate.builder().firstName("Mari").lastName("Maasikas").build();
        val newApplication = Application.builder().candidate(newCandidate).build();

        val applicationSaved = applicationService.insertApplication(newApplication);
        val initialUpdatedOn = applicationSaved.getUpdatedOn();
        assertEquals(NEW, applicationSaved.getApplicationState());

        val stateMachine = applicationStateMachine.rejectApplication(applicationSaved.getId());
        assertEquals(REJECTED, stateMachine.getState().getId());

        val optionalRejectedApplication = applicationRepository.findById(applicationSaved.getId());
        assertFalse(optionalRejectedApplication.isEmpty());

        val rejectedApplication = optionalRejectedApplication.get();
        assertEquals(REJECTED, rejectedApplication.getApplicationState());
        assertNotEquals(initialUpdatedOn, rejectedApplication.getUpdatedOn());

    }

    @Test
    @Transactional
    public void interviewGotScheduled(){
        val newCandidate = Candidate.builder().firstName("Mari").lastName("Maasikas").build();
        val newApplication = Application.builder().candidate(newCandidate).build();

        val applicationSaved = applicationService.insertApplication(newApplication);
        val interviewSaved = interviewService.scheduleInterview(applicationSaved, INFORMAL,
                OffsetDateTime.now(), "Karl Hunt");

        val stateMachine = applicationStateMachine.scheduleInterview(interviewSaved.getApplication().getId());
        assertEquals(INTERVIEW, stateMachine.getState().getId());

        val optionalInterview = interviewRepository.findById(interviewSaved.getId());
        assertFalse(optionalInterview.isEmpty());
    }

    @Test
    @Transactional
    public void scheduleInterviewChangesApplicationState(){
        val newCandidate = Candidate.builder().firstName("Mari").lastName("Maasikas").build();
        val newApplication = Application.builder().candidate(newCandidate).build();

        val applicationSaved = applicationService.insertApplication(newApplication);
        val interviewSaved = interviewService.scheduleInterview(applicationSaved, INFORMAL,
                OffsetDateTime.now(), "Karl Hunt");

        val optionalInterview = interviewRepository.findById(interviewSaved.getId());
        assertFalse(optionalInterview.isEmpty());

        val interviewApplication = optionalInterview.get().getApplication();
        assertEquals(INTERVIEW, interviewApplication.getApplicationState());
    }

    @Test
    @Transactional
    public void rejectInterviewedCandidate(){
        val newCandidate = Candidate.builder().firstName("Mari").lastName("Maasikas").build();
        val newApplication = Application.builder().candidate(newCandidate).build();

        val applicationSaved = applicationService.insertApplication(newApplication);
        val interviewSaved = interviewService.scheduleInterview(applicationSaved, INFORMAL,
                OffsetDateTime.now(), "Karl Hunt");

        val stateMachine = applicationStateMachine.rejectApplication(interviewSaved.getApplication().getId());
        assertEquals(REJECTED, stateMachine.getState().getId());
    }
}
