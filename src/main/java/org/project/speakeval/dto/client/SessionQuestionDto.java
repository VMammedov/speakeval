package org.project.speakeval.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionQuestionDto {
    private String questionId;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private MultipartFile file;
    private String format;
    private Long durationMs;
    private Long sizeBytes;
    private Integer sequence;
}
