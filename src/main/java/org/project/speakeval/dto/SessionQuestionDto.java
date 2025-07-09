package org.project.speakeval.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SessionQuestionDto {
    String questionId;
    LocalDateTime startedAt;
    LocalDateTime endedAt;
    MultipartFile file;
    String format;
    Long durationMs;
    Long sizeBytes;
    Integer sequence;
}
