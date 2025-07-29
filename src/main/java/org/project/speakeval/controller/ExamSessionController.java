package org.project.speakeval.controller;

import lombok.RequiredArgsConstructor;
import org.project.speakeval.constants.Constants;
import org.project.speakeval.domain.User;
import org.project.speakeval.dto.client.request.exam_session.UpdateExamSessionRequest;
import org.project.speakeval.dto.client.response.ExamResultResponse;
import org.project.speakeval.dto.client.response.exam_session.ExamSessionDetailResponse;
import org.project.speakeval.dto.client.response.exam_session.CreateExamSessionResponse;
import org.project.speakeval.dto.client.response.exam_session.UpdateExamSessionResponse;
import org.project.speakeval.dto.management.response.ExamSessionSummaryResponse;
import org.project.speakeval.services.ExamSessionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping(value = "/{examSessionId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UpdateExamSessionResponse updateExamSession(@AuthenticationPrincipal User user,
                                                       @PathVariable String examSessionId,
                                                       @RequestPart("metadata") UpdateExamSessionRequest request,
                                                       @RequestPart("files") List<MultipartFile> files) {
        return examSessionService.updateExamSession(request, examSessionId, files, user);
    }

    @GetMapping("/{examSessionId}")
    public ExamSessionDetailResponse getExamSession(@AuthenticationPrincipal User user,
                                                    @PathVariable String examSessionId) {
        return examSessionService.getExamSessionById(examSessionId, user);
    }

    @GetMapping("/my-sessions")
    public Page<ExamSessionSummaryResponse> getMyExamSessions(@AuthenticationPrincipal User user,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "20") int size,
                                                              @RequestParam(defaultValue = "startedAt") String sortBy,
                                                              @RequestParam(defaultValue = Constants.Order.DESC) String sortDirection) {
        return examSessionService.getUserExamSessions(user.getId(), PageRequest.of(page, size,
                sortDirection.equalsIgnoreCase(Constants.Order.DESC) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()));
    }

    @DeleteMapping("/{examSessionId}")
    public void cancelExamSession(@AuthenticationPrincipal User user,
                                                  @PathVariable String examSessionId) {
        examSessionService.cancelExamSession(examSessionId, user);
    }

    @GetMapping("/{examSessionId}/results")
    public ExamResultResponse getExamResults(@AuthenticationPrincipal User user,
                                             @PathVariable String examSessionId) {
        return examSessionService.getExamResults(examSessionId, user);
    }
}
