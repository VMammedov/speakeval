package org.project.speakeval.controller;

import lombok.RequiredArgsConstructor;
import org.project.speakeval.domain.User;
import org.project.speakeval.dto.request.exam_session.UpdateExamSessionRequest;
import org.project.speakeval.dto.response.exam_session.CreateExamSessionResponse;
import org.project.speakeval.dto.response.exam_session.UpdateExamSessionResponse;
import org.project.speakeval.services.ExamSessionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
public class ExamSessionController {

    private final ExamSessionService examSessionService;

    @PostMapping
    public CreateExamSessionResponse createExamSession(@AuthenticationPrincipal User user) {
        return examSessionService.createExamSession(user);
    }

    @PostMapping("/{examSessionId}")
    public UpdateExamSessionResponse updateExamSession(@AuthenticationPrincipal User user,
                                                       @PathVariable String examSessionId,
                                                       UpdateExamSessionRequest request) {
        return examSessionService.updateExamSession(request, examSessionId, user);
    }
}
