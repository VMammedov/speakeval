package org.project.speakeval.dto.response.exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ExamResponse {

    @EqualsAndHashCode.Include
    private String id;
    private String title;
    private String description;
    private Integer passScore;
    private Integer questionCount;
}
