package org.project.speakeval.dto.request.exam_session;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.project.speakeval.dto.SessionQuestionDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateExamSessionRequest {
    LocalDateTime endTime;
    List<SessionQuestionDto> sessionQuestions;
}
