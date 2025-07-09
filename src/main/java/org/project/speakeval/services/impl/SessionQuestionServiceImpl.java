package org.project.speakeval.services.impl;

import lombok.RequiredArgsConstructor;
import org.project.speakeval.domain.SessionQuestion;
import org.project.speakeval.repository.SessionQuestionRepository;
import org.project.speakeval.services.SessionQuestionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionQuestionServiceImpl implements SessionQuestionService {

    private final SessionQuestionRepository sessionQuestionRepository;

    public SessionQuestion createSessionQuestion(SessionQuestion sessionQuestion) {
        return sessionQuestionRepository.save(sessionQuestion);
    }
}
