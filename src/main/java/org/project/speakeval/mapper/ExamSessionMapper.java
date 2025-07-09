package org.project.speakeval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.project.speakeval.domain.ExamSession;
import org.project.speakeval.dto.response.exam_session.CreateExamSessionResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExamSessionMapper {

    @Mapping(target = "userId", source = "user.id")
    CreateExamSessionResponse toCreateExamSessionResponse(ExamSession examSession);

}
