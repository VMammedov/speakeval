package org.project.speakeval.services;

import org.project.speakeval.domain.Question;

public interface QuestionService {
    Question getQuestionById(String id);
}
