package org.project.speakeval.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.speakeval.constants.Constants;
import org.project.speakeval.dto.management.request.ExamSessionFilterRequest;
import org.project.speakeval.dto.management.request.UpdateScoreRequest;
import org.project.speakeval.dto.management.response.ExamSessionManagementDetailResponse;
import org.project.speakeval.dto.management.response.ExamSessionManagementResponse;
import org.project.speakeval.dto.management.response.ExamSessionStatisticsResponse;
import org.project.speakeval.dto.management.response.ExamSessionSummaryResponse;
import org.project.speakeval.enums.SessionStatus;
import org.project.speakeval.services.ExamSessionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/admin/exam-sessions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class ExamSessionManagementController {

    private final ExamSessionService examSessionService;

    @GetMapping
    public Page<ExamSessionManagementResponse> getAllExamSessions(@RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size,
                                                                           @RequestParam(defaultValue = "startedAt") String sortBy,
                                                                           @RequestParam(defaultValue = "desc") String sortDirection,
                                                                           @RequestParam(required = false) SessionStatus status,
                                                                           @RequestParam(required = false) String userId,
                                                                           @RequestParam(required = false) String examId,
                                                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        ExamSessionFilterRequest filter = ExamSessionFilterRequest.builder()
                .status(status)
                .userId(userId)
                .examId(examId)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        return examSessionService.getAllExamSessionsFiltered(filter, PageRequest.of(page, size,
                sortDirection.equalsIgnoreCase(Constants.Order.DESC) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()));
    }

    @GetMapping("/{examSessionId}")
    public ExamSessionManagementDetailResponse getExamSessionDetail(@PathVariable String examSessionId) {
        return examSessionService.getExamSessionDetailForAdmin(examSessionId);
    }

    @PutMapping("/{examSessionId}/score")
    public void updateExamScore(@PathVariable String examSessionId,
                                @RequestBody @Valid UpdateScoreRequest request) {
        examSessionService.updateExamScore(examSessionId, request);
    }

    @DeleteMapping("/{examSessionId}")
    public void deleteExamSession(@PathVariable String examSessionId) {
        examSessionService.deleteExamSession(examSessionId);
    }

    @PostMapping("/{examSessionId}/reopen")
    public void reopenExamSession(@PathVariable String examSessionId) {
        examSessionService.reopenExamSession(examSessionId);
    }

    @GetMapping("/statistics")
    public ExamSessionStatisticsResponse getStatistics(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String examId) {
        return examSessionService.getExamSessionStatistics(startDate, endDate, examId);
    }

    @PutMapping("/{examSessionId}/questions/{questionId}/score")
    public void updateQuestionScore(@PathVariable String examSessionId,
                                    @PathVariable String questionId,
                                    @RequestBody @Valid UpdateScoreRequest request) {
        examSessionService.updateQuestionScore(examSessionId, questionId, request);
    }

    @GetMapping("/users/{userId}")
    public Page<ExamSessionSummaryResponse> getUserExamSessions(@PathVariable String userId,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "20") int size) {
        return examSessionService.getUserExamSessions(userId, PageRequest.of(page, size));
    }
}
