package org.project.speakeval.dto.client.request.exam_session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.speakeval.dto.client.SessionQuestionDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExamSessionRequest {
    private LocalDateTime endTime;
    private List<SessionQuestionDto> sessionQuestions;
}
