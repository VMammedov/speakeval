package org.project.speakeval.dto.management.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.speakeval.dto.client.response.exam.ExamSummaryResponse;
import org.project.speakeval.enums.SessionStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamSessionManagementDetailResponse {
    private String id;
    private UserSummaryResponse user;
    private ExamSummaryResponse exam;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private SessionStatus status;
    private Integer currentQuestionIndex;
    private Integer totalScore;
    private List<SessionQuestionDetailResponse> questions;
    private Map<String, Object> metadata;
}
