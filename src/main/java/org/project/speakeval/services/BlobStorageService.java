package org.project.speakeval.services;

import org.springframework.web.multipart.MultipartFile;

public interface BlobStorageService {
    String upload(MultipartFile file,
                  String fileName,
                  String relatedEntityType,
                  String relatedEntityId);
}
