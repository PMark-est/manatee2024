package ee.cyber.manatee.service;

import ee.cyber.manatee.model.Application;
import ee.cyber.manatee.model.Interview;
import ee.cyber.manatee.repository.InterviewRepository;
import ee.cyber.manatee.statemachine.ApplicationStateMachine;
import ee.cyber.manatee.statemachine.InterviewType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterviewService {
    private final InterviewRepository interviewRepository;
    private final ApplicationStateMachine applicationStateMachine;

    public Interview scheduleInterview(Application application, InterviewType interviewType, OffsetDateTime interviewTime, String interviewerName){
        applicationStateMachine.scheduleInterview(application.getId());
        application.setUpdatedOn(OffsetDateTime.now());
        Interview interview = Interview.builder()
                .application(application)
                .interviewType(interviewType)
                .interviewTime(interviewTime)
                .interviewerName(interviewerName)
                .build();

        application.addInterview(interview);
        return interviewRepository.save(interview);
    }

    public List<Interview> getInterviews(){
        return interviewRepository.findAll();
    }

    public List<Interview> getInterview(Integer applicationId){
        return interviewRepository.findAllByApplicationId(applicationId);
    }
}
