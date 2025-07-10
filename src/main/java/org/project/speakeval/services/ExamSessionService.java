package org.project.speakeval.services;

import org.project.speakeval.domain.User;
import org.project.speakeval.dto.request.exam_session.UpdateExamSessionRequest;
import org.project.speakeval.dto.response.exam_session.CreateExamSessionResponse;
import org.project.speakeval.dto.response.exam_session.UpdateExamSessionResponse;

public interface ExamSessionService {

    CreateExamSessionResponse createExamSession(User user);
    UpdateExamSessionResponse updateExamSession(UpdateExamSessionRequest request, String examSessionId, User user);
}
