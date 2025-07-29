package org.project.speakeval.dto.management.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamSessionStatisticsResponse {
    private Long totalSessions;
    private Long completedSessions;
    private Long inProgressSessions;
    private Long cancelledSessions;
    private Double averageScore;
    private Double completionRate;
    private Long averageDurationMinutes;
    private List<PopularExamResponse> popularExams;
    private Map<String, Long> dailySessionCounts;
}
