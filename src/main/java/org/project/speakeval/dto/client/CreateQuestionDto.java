package org.project.speakeval.dto.client;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.speakeval.enums.QuestionType;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuestionDto {

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    private Integer thinkDurationSeconds;
    private Integer answerDurationSeconds;
    private String promptText;
    private MultipartFile file;
    private String format;

    @NotBlank
    @Positive
    private Integer sequence;
}
