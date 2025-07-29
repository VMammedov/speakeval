package org.project.speakeval.services.impl;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.project.speakeval.constants.Constants;
import org.project.speakeval.domain.AudioFile;
import org.project.speakeval.domain.Exam;
import org.project.speakeval.domain.ExamSession;
import org.project.speakeval.domain.Question;
import org.project.speakeval.domain.SessionQuestion;
import org.project.speakeval.domain.User;
import org.project.speakeval.dto.client.SessionQuestionDto;
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
import org.project.speakeval.enums.OperationResult;
import org.project.speakeval.enums.SessionStatus;
import org.project.speakeval.mapper.ExamSessionMapper;
import org.project.speakeval.repository.ExamRepository;
import org.project.speakeval.repository.ExamSessionRepository;
import org.project.speakeval.services.BlobStorageService;
import org.project.speakeval.services.ExamSessionService;
import org.project.speakeval.services.QuestionService;
import org.project.speakeval.services.SessionQuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ExamSessionServiceImpl implements ExamSessionService {

    private final ExamSessionRepository examSessionRepository;
    private final ExamRepository examRepository;
    private final ExamSessionMapper examSessionMapper;
    private final BlobStorageService blobStorageService;
    private final QuestionService questionService;
    private final SessionQuestionService sessionQuestionService;

    public CreateExamSessionResponse createExamSession(User user) {
        Exam exam = examRepository.findRandomExamWithQuestions()
                .orElseThrow(() -> new EntityNotFoundException("There is no exam at this time"));
        ExamSession examSession = ExamSession.builder()
                .exam(exam)
                .startedAt(LocalDateTime.now())
                .status(SessionStatus.IN_PROGRESS)
                .user(user)
                .build();

        return examSessionMapper.toCreateExamSessionResponse(examSessionRepository.save(examSession));
    }

    @Override
    @Transactional
    public UpdateExamSessionResponse updateExamSession(UpdateExamSessionRequest request,
                                                       String examSessionId,
                                                       List<MultipartFile> files,
                                                       User user) {
        ExamSession examSession = examSessionRepository.findById(examSessionId)
                .orElseThrow(() -> new EntityNotFoundException("There is no exam by this Id."));

        if (!examSession.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You can only update your own exam sessions");
        }

        if (examSession.getStatus() != SessionStatus.IN_PROGRESS) {
            throw new IllegalStateException("Can only update sessions that are in progress");
        }

        for (SessionQuestionDto dto : request.getSessionQuestions()) {
            String blobName = String.format(Constants.BlobNamingFormat.SESSION_AUDIO_ANSWER_FORMAT,
                    examSession.getId(),
                    dto.getQuestionId(),
                    dto.getSequence(),
                    dto.getFormat());

            String url = blobStorageService.upload(
                    dto.getFile(),
                    blobName,
                    Constants.RelatedEntityType.QUESTION,
                    dto.getQuestionId());

            AudioFile audio = AudioFile.builder()
                    .storageUrl(url)
                    .format(dto.getFormat())
                    .durationMs(dto.getDurationMs())
                    .sizeBytes(dto.getSizeBytes())
                    .build();

            Question question = questionService.getQuestionById(dto.getQuestionId());

            SessionQuestion sessionQuestion = SessionQuestion.builder()
                    .examSession(examSession)
                    .question(question)
                    .sequence(dto.getSequence())
                    .startedAt(dto.getStartedAt())
                    .endedAt(dto.getEndedAt())
                    .audioFile(audio)
                    .build();

            sessionQuestionService.createSessionQuestion(sessionQuestion);
        }

        examSession.setEndedAt(request.getEndTime());
        examSession.setStatus(SessionStatus.COMPLETED);
        examSessionRepository.save(examSession);

        return new UpdateExamSessionResponse(OperationResult.SUCCESS);
    }

    @Override
    public ExamSessionDetailResponse getExamSessionById(String examSessionId, User user) {
        return examSessionMapper.toExamSessionDetailResponse(examSessionRepository.findByIdAndUserId(examSessionId, user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Session Not Found!")));
    }

    @Override
    public Page<ExamSessionSummaryResponse> getUserExamSessions(String userId, PageRequest pageRequest) {
        return examSessionRepository.findByUserId(userId, pageRequest)
                .map(examSessionMapper::toExamSessionSummaryResponse);
    }

    @Override
    public void cancelExamSession(String examSessionId, User user) {

    }

    @Override
    public ExamResultResponse getExamResults(String examSessionId, User user) {
        ExamSession examSession = examSessionRepository.findById(examSessionId)
                .orElseThrow(() -> new EntityNotFoundException("Exam Session Not Found!"));
        return null;
    }

    @Override
    public Page<ExamSessionManagementResponse> getAllExamSessionsFiltered(ExamSessionFilterRequest filter, PageRequest pageRequest) {
        return null;
    }

    @Override
    public ExamSessionManagementDetailResponse getExamSessionDetailForAdmin(String examSessionId) {
        return null;
    }

    @Override
    public void updateExamScore(String examSessionId, UpdateScoreRequest request) {

    }

    @Override
    public void deleteExamSession(String examSessionId) {

    }

    @Override
    public void reopenExamSession(String examSessionId) {

    }

    @Override
    public ExamSessionStatisticsResponse getExamSessionStatistics(LocalDate startDate, LocalDate endDate, String examId) {
        return null;
    }

}
