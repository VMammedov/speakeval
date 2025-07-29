package org.project.speakeval.dto.client.response.exam_session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.speakeval.enums.SessionStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamSessionDetailResponse {
    private String id;
    private String examId;
    private String examTitle;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private SessionStatus status;
    private Integer totalScore;
}
