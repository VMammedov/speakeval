package org.project.speakeval.dto.management;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.speakeval.enums.QuestionType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResultDto {
    private Integer sequence;
    private QuestionType type;
    private Integer thinkDurationSeconds;
    private Integer answerDurationSeconds;
    private String promptText;
    private String promptResourceUrl;
}
