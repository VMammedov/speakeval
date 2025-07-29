package org.project.speakeval.services;

import org.project.speakeval.domain.User;
import org.project.speakeval.dto.client.request.exam_session.UpdateExamSessionRequest;
import org.project.speakeval.dto.client.response.ExamResultResponse;
import org.project.speakeval.dto.client.response.exam_session.CreateExamSessionResponse;
import org.project.speakeval.dto.client.response.exam_session.ExamSessionDetailResponse;
import org.project.speakeval.dto.client.response.exam_session.UpdateExamSessionResponse;
import org.project.speakeval.dto.management.request.ExamSessionFilterRequest;
import org.project.speakeval.dto.management.request.UpdateScoreRequest;
import org.project.speakeval.dto.management.response.ExamSessionManagementDetailResponse;
import org.project.speakeval.dto.management.response.ExamSessionManagementResponse;
import org.project.speakeval.dto.management.response.ExamSessionStatisticsResponse;
import org.project.speakeval.dto.management.response.ExamSessionSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface ExamSessionService {

    CreateExamSessionResponse createExamSession(User user);
    UpdateExamSessionResponse updateExamSession(UpdateExamSessionRequest request,
                                                String examSessionId,
                                                List<MultipartFile> files,
                                                User user);
    ExamSessionDetailResponse getExamSessionById(String examSessionId, User user);
    Page<ExamSessionSummaryResponse> getUserExamSessions(String userId, PageRequest pageRequest);
    void cancelExamSession(String examSessionId, User user);
    ExamResultResponse getExamResults(String examSessionId, User user);

    Page<ExamSessionManagementResponse> getAllExamSessionsFiltered(ExamSessionFilterRequest filter, PageRequest pageRequest);
    ExamSessionManagementDetailResponse getExamSessionDetailForAdmin(String examSessionId);
    void updateExamScore(String examSessionId, UpdateScoreRequest request);
    void deleteExamSession(String examSessionId);
    void reopenExamSession(String examSessionId);
    ExamSessionStatisticsResponse getExamSessionStatistics(LocalDate startDate, LocalDate endDate, String examId);
}
