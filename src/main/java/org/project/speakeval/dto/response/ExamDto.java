package org.project.speakeval.dto.response;

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
    Integer passScore;
    List<QuestionDto> questions;

}
