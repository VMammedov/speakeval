package org.project.speakeval.dto.response.exam_session;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.project.speakeval.dto.ExamDto;
import org.project.speakeval.enums.SessionStatus;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateExamSessionResponse {

    String id;
    String userId;
    ExamDto exam;
    LocalDateTime startedAt;
    SessionStatus status;

}
