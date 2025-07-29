package org.project.speakeval.dto.management.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.speakeval.enums.SessionStatus;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamSessionFilterRequest {
    private SessionStatus status;
    private String userId;
    private String examId;
    private LocalDate startDate;
    private LocalDate endDate;
}
