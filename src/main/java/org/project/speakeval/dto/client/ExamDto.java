package org.project.speakeval.dto.client;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamDto {

    private String id;
    private String title;
    private String description;
    private List<QuestionDto> questions;
}
