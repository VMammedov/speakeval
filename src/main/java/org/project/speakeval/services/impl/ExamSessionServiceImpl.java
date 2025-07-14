package org.project.speakeval.services.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.project.speakeval.domain.AudioFile;
import org.project.speakeval.domain.Exam;
import org.project.speakeval.domain.ExamSession;
import org.project.speakeval.domain.Question;
import org.project.speakeval.domain.SessionQuestion;
import org.project.speakeval.domain.User;
import org.project.speakeval.dto.SessionQuestionDto;
import org.project.speakeval.dto.request.exam_session.UpdateExamSessionRequest;
import org.project.speakeval.dto.response.exam_session.CreateExamSessionResponse;
import org.project.speakeval.dto.response.exam_session.UpdateExamSessionResponse;
import org.project.speakeval.enums.SessionStatus;
import org.project.speakeval.exception.MissingFileException;
import org.project.speakeval.mapper.ExamSessionMapper;
import org.project.speakeval.repository.ExamSessionRepository;
import org.project.speakeval.services.ExamService;
import org.project.speakeval.services.ExamSessionService;
import org.project.speakeval.services.QuestionService;
import org.project.speakeval.services.SessionQuestionService;
import org.project.speakeval.util.FileUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExamSessionServiceImpl implements ExamSessionService {

    private final ExamService examService;
    private final ExamSessionRepository examSessionRepository;
    private final ExamSessionMapper examSessionMapper;
    private final BlobContainerClient audioAnswersBlobContainerClient;
    private final QuestionService questionService;
    private final SessionQuestionService sessionQuestionService;

    public ExamSessionServiceImpl(ExamService examService,
                                  ExamSessionRepository examSessionRepository,
                                  ExamSessionMapper examSessionMapper,
                                  @Qualifier(value = "audioAnswersBlobContainerClient") BlobContainerClient audioAnswersBlobContainerClient,
                                  QuestionService questionService,
                                  SessionQuestionService sessionQuestionService) {
        this.examService = examService;
        this.examSessionRepository = examSessionRepository;
        this.examSessionMapper = examSessionMapper;
        this.audioAnswersBlobContainerClient = audioAnswersBlobContainerClient;
        this.questionService = questionService;
        this.sessionQuestionService = sessionQuestionService;
    }

    public CreateExamSessionResponse createExamSession(User user) {
        Exam exam = examService.getRandomExam();
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

        Map<String, MultipartFile> fileMap = files.stream().collect(Collectors.toMap(
                f -> FileUtil.getFileNameWithoutExtension(f.getOriginalFilename()),
                Function.identity()
        ));

        for (SessionQuestionDto dto : request.getSessionQuestions()) {
            MultipartFile file = fileMap.get(dto.getQuestionId());
            if (file == null) {
                throw new MissingFileException("Missing file for question " + dto.getQuestionId());
            }

            String blobName = String.format("sessions/%s/q-%s-%03d.%s",
                    examSession.getId(),
                    dto.getQuestionId(),
                    dto.getSequence(),
                    dto.getFormat());

            BlobClient blobClient = audioAnswersBlobContainerClient.getBlobClient(blobName);

            try (InputStream in = file.getInputStream()) {
                blobClient.upload(in, file.getSize(), true);
            } catch (IOException e) {
                throw new UncheckedIOException("Failed to upload audio for question " +
                        dto.getQuestionId(), e);
            }

            String url = blobClient.getBlobUrl();

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

        return new UpdateExamSessionResponse();
    }

}
