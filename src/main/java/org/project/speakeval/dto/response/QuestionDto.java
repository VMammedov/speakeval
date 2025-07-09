package org.project.speakeval.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.project.speakeval.enums.QuestionType;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionDto {

    String id;
    QuestionType type;
    Integer thinkDurationSeconds;
    Integer answerDurationSeconds;
    String promptText;
    String promptResourceUrl;

}
