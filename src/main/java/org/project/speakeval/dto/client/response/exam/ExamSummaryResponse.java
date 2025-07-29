package org.project.speakeval.dto.client.response.exam;

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
public class ExamSummaryResponse {

    @EqualsAndHashCode.Include
    private String id;
    private String title;
    private Integer passScore;
    private Integer questionCount;
}

