package org.project.speakeval.dto.client.response.exam_session;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.speakeval.dto.client.ExamDto;
import org.project.speakeval.enums.SessionStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateExamSessionResponse {

    private String id;
    private String userId;
    private ExamDto exam;
    private LocalDateTime startedAt;
    private SessionStatus status;

}
