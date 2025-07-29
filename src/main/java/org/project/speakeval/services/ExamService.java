package org.project.speakeval.services;

import org.project.speakeval.dto.client.request.exam.CreateExamRequest;
import org.project.speakeval.dto.client.request.exam.UpdateExamRequest;
import org.project.speakeval.dto.client.response.exam.ExamResponse;
import org.project.speakeval.dto.client.response.exam.ExamSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExamService {

    Page<ExamSummaryResponse> getAllExams(Pageable pageable);

    Page<ExamSummaryResponse> searchExams(String title, Integer minScore, Integer minQuestions, Pageable pageable);

    ExamResponse getExamById(String id);

    ExamResponse getExamByTitle(String title);

    ExamResponse createExam(CreateExamRequest request);

    ExamResponse updateExam(String id, UpdateExamRequest request);

    void deleteExam(String id);

    boolean existsById(String id);

    boolean existsByTitle(String title);

    Page<ExamSummaryResponse> getExamsByTitleContaining(String title, Pageable pageable);

    int updatePassScore(String id, Integer newScore);

    ExamResponse duplicateExam(String id, String newTitle);
}
