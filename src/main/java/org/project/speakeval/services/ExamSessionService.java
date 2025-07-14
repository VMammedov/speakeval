package org.project.speakeval.services;

import org.project.speakeval.domain.User;
import org.project.speakeval.dto.request.exam_session.UpdateExamSessionRequest;
import org.project.speakeval.dto.response.exam_session.CreateExamSessionResponse;
import org.project.speakeval.dto.response.exam_session.UpdateExamSessionResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExamSessionService {

    CreateExamSessionResponse createExamSession(User user);
    UpdateExamSessionResponse updateExamSession(UpdateExamSessionRequest request,
                                                String examSessionId,
                                                List<MultipartFile> files,
                                                User user);
}
