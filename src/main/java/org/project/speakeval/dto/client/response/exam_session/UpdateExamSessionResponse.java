package org.project.speakeval.dto.client.response.exam_session;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.speakeval.enums.OperationResult;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExamSessionResponse {
    @Enumerated(EnumType.STRING)
    private OperationResult operationResult;
}
