package org.project.speakeval.services;

import org.project.speakeval.domain.User;
import org.project.speakeval.dto.response.exam_session.CreateExamSessionResponse;

public interface ExamSessionService {

    CreateExamSessionResponse createExamSession(User user);

}
