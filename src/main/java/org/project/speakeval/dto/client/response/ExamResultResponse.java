package org.project.speakeval.dto.client.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.speakeval.dto.management.QuestionResultDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamResultResponse {
    private String sessionId;
    private String examTitle;
    private Integer totalScore;
    private Integer maxScore;
    private Double percentage;
    private LocalDateTime completedAt;
    private Long durationMinutes;
    private List<QuestionResultDto> questionResults;
}
