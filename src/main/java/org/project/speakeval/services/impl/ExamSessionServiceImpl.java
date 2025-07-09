package org.project.speakeval.services.impl;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.project.speakeval.domain.Exam;
import org.project.speakeval.domain.ExamSession;
import org.project.speakeval.domain.User;
import org.project.speakeval.dto.response.exam_session.CreateExamSessionResponse;
import org.project.speakeval.enums.SessionStatus;
import org.project.speakeval.mapper.ExamSessionMapper;
import org.project.speakeval.repository.ExamSessionRepository;
import org.project.speakeval.services.ExamService;
import org.project.speakeval.services.ExamSessionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamSessionServiceImpl implements ExamSessionService {

    private final ExamService examService;
    private final ExamSessionRepository examSessionRepository;
    private final ExamSessionMapper examSessionMapper;

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

}
