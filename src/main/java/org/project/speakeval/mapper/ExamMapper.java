package org.project.speakeval.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.project.speakeval.domain.Exam;
import org.project.speakeval.dto.client.request.exam.CreateExamRequest;
import org.project.speakeval.dto.client.request.exam.UpdateExamRequest;
import org.project.speakeval.dto.client.response.exam.ExamResponse;
import org.project.speakeval.dto.client.response.exam.ExamSummaryResponse;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ExamMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "questions", ignore = true)
    Exam toExam(CreateExamRequest request);

    @Mapping(target = "questionCount", expression = "java(getQuestionCount(exam))")
    ExamResponse toResponse(Exam exam);

    @Mapping(target = "questionCount", expression = "java(getQuestionCount(exam))")
    ExamSummaryResponse toSummaryResponse(Exam exam);

    List<ExamResponse> toResponseList(List<Exam> exams);

    List<ExamSummaryResponse> toSummaryResponseList(List<Exam> exams);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "questions", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(UpdateExamRequest request, @MappingTarget Exam exam);

    default Integer getQuestionCount(Exam exam) {
        return exam.getQuestions() != null ? exam.getQuestions().size() : 0;
    }
}