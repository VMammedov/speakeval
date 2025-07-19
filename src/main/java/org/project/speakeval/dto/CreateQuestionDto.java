package org.project.speakeval.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.project.speakeval.enums.QuestionType;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateQuestionDto {

    @Enumerated(EnumType.STRING)
    QuestionType type;

    Integer thinkDurationSeconds;
    Integer answerDurationSeconds;
    String promptText;
    MultipartFile file;
    String format;

    @NotBlank
    @Positive
    private Integer sequence;
}
