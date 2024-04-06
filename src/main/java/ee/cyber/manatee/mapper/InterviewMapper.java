package ee.cyber.manatee.mapper;

import ee.cyber.manatee.dto.ApplicationDto;
import ee.cyber.manatee.dto.InterviewDto;
import ee.cyber.manatee.dto.InterviewTypeDto;
import ee.cyber.manatee.model.Application;
import ee.cyber.manatee.model.Interview;
import ee.cyber.manatee.statemachine.InterviewType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InterviewMapper {

    InterviewDto entityToDto(Interview entity);
    InterviewTypeDto entityToDto(InterviewType entity);

    Interview dtoToEntity(InterviewDto dto);
    InterviewType dtoToEntity(InterviewTypeDto dto);

    List<InterviewDto> entitiesToDtoList(List<Interview> entity);
}
