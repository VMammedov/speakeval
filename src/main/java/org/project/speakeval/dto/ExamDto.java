package org.project.speakeval.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExamDto {

    String id;
    String title;
    String description;
    List<QuestionDto> questions;

}
