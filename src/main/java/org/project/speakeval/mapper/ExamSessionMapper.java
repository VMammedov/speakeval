package org.project.speakeval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.project.speakeval.domain.ExamSession;
import org.project.speakeval.dto.client.response.exam_session.CreateExamSessionResponse;
import org.project.speakeval.dto.client.response.exam_session.ExamSessionDetailResponse;
import org.project.speakeval.dto.management.response.ExamSessionSummaryResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExamSessionMapper {

    @Mapping(target = "userId", source = "user.id")
    CreateExamSessionResponse toCreateExamSessionResponse(ExamSession examSession);

    @Mapping(target = "examId", source = "examSession.exam.id")
    @Mapping(target = "examTitle", source = "examSession.exam.title")
    ExamSessionDetailResponse toExamSessionDetailResponse(ExamSession examSession);

    @Mapping(target = "examId", source = "examSession.exam.id")
    @Mapping(target = "examTitle", source = "examSession.exam.title")
    @Mapping(target = "totalQuestions", expression = "java(sessionQuestions.size())")
    ExamSessionSummaryResponse toExamSessionSummaryResponse(ExamSession examSession);
}
