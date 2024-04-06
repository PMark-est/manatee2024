package ee.cyber.manatee.controller;

import ee.cyber.manatee.api.InterviewApi;
import ee.cyber.manatee.dto.InterviewDto;
import ee.cyber.manatee.dto.InterviewTypeDto;
import ee.cyber.manatee.mapper.InterviewMapper;
import ee.cyber.manatee.service.InterviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InterviewApiImpl implements InterviewApi {
    private final InterviewMapper interviewMapper;
    private final InterviewService interviewService;

    @Override
    public ResponseEntity<List<InterviewDto>> getInterview(Integer applicationId) {
        val applicationInterviews = interviewService.getInterview(applicationId);
        return ResponseEntity.ok(interviewMapper.entitiesToDtoList(applicationInterviews));
    }

    @Override
    public ResponseEntity<List<InterviewDto>> getInterviews() {
        val allInterviews = interviewService.getInterviews();
        return ResponseEntity.ok(interviewMapper.entitiesToDtoList(allInterviews));
    }

    @Override
    public ResponseEntity<InterviewDto> scheduleInterview(InterviewTypeDto interviewType, String interviewerName,
                                                          OffsetDateTime interviewTime, InterviewDto interviewDto) {
        val draftInterviewType = interviewMapper.dtoToEntity(interviewType);
        val draftInterview = interviewMapper.dtoToEntity(interviewDto);
        val scheduledInterview = interviewService.scheduleInterview(draftInterview.getApplication(), draftInterviewType, interviewTime, interviewerName);
        return ResponseEntity.status(ACCEPTED).body(interviewMapper.entityToDto(scheduledInterview));
    }
}

