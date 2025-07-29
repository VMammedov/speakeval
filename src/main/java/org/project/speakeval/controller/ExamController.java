package org.project.speakeval.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.project.speakeval.dto.client.request.exam.CreateExamRequest;
import org.project.speakeval.dto.client.request.exam.UpdateExamRequest;
import org.project.speakeval.dto.client.response.exam.ExamResponse;
import org.project.speakeval.dto.client.response.exam.ExamSummaryResponse;
import org.project.speakeval.services.ExamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exams")
@Validated
public class ExamController {

    private final ExamService examService;

    @GetMapping
    public Page<ExamSummaryResponse> getAllExams(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) @Min(0) Integer minScore,
            @RequestParam(required = false) @Min(0) Integer minQuestions) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        if (title != null || minScore != null || minQuestions != null) {
            return examService.searchExams(title, minScore, minQuestions, pageable);
        }

        return examService.getAllExams(pageable);
    }

    @GetMapping("/{id}")
    public ExamResponse getExamById(@PathVariable @NotBlank String id) {
        return examService.getExamById(id);
    }

    @GetMapping("/title/{title}")
    public ExamResponse getExamByTitle(@PathVariable @NotBlank String title) {
        return examService.getExamByTitle(title);
    }

    @GetMapping("/search/title")
    public Page<ExamSummaryResponse> searchByTitle(
            @RequestParam @NotBlank String title,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return examService.getExamsByTitleContaining(title, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExamResponse createExam(@Valid @RequestBody CreateExamRequest request) {
        return examService.createExam(request);
    }

    @PutMapping("/{id}")
    public ExamResponse updateExam(
            @PathVariable @NotBlank String id,
            @Valid @RequestBody UpdateExamRequest request) {
        return examService.updateExam(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExam(@PathVariable @NotBlank String id) {
        examService.deleteExam(id);
    }

    @GetMapping("/{id}/exists")
    public Map<String, Boolean> checkExamExists(@PathVariable @NotBlank String id) {
        return Map.of("exists", examService.existsById(id));
    }

    @GetMapping("/title/{title}/exists")
    public Map<String, Boolean> checkExamExistsByTitle(@PathVariable @NotBlank String title) {
        return Map.of("exists", examService.existsByTitle(title));
    }

    @PatchMapping("/{id}/pass-score")
    public Map<String, String> updatePassScore(
            @PathVariable @NotBlank String id,
            @RequestParam @Min(0) @Max(100) Integer passScore) {
        int updatedRows = examService.updatePassScore(id, passScore);
        return Map.of("message", updatedRows > 0 ? "Pass score updated successfully" : "No changes made");
    }

    @PostMapping("/{id}/duplicate")
    @ResponseStatus(HttpStatus.CREATED)
    public ExamResponse duplicateExam(
            @PathVariable @NotBlank String id,
            @RequestParam @NotBlank String newTitle) {
        return examService.duplicateExam(id, newTitle);
    }
}

