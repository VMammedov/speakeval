package org.project.speakeval.controller;

import lombok.RequiredArgsConstructor;
import org.project.speakeval.domain.User;
import org.project.speakeval.dto.request.exam_session.UpdateExamSessionRequest;
import org.project.speakeval.dto.response.exam_session.CreateExamSessionResponse;
import org.project.speakeval.dto.response.exam_session.UpdateExamSessionResponse;
import org.project.speakeval.services.ExamSessionService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
public class ExamSessionController {

    private final ExamSessionService examSessionService;

    @PostMapping
    public CreateExamSessionResponse createExamSession(@AuthenticationPrincipal User user) {
        return examSessionService.createExamSession(user);
    }

    @PostMapping(value = "/{examSessionId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UpdateExamSessionResponse updateExamSession(@AuthenticationPrincipal User user,
                                                       @PathVariable String examSessionId,
                                                       @RequestPart("metadata") UpdateExamSessionRequest request,
                                                       @RequestPart("files") List<MultipartFile> files) {
        return examSessionService.updateExamSession(request, examSessionId, files, user);
    }
}
