package org.project.speakeval.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "audio_files")
public class AudioFile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String storageUrl;
    private String format;
    private Long durationMs;
    private Long sizeBytes;
}
