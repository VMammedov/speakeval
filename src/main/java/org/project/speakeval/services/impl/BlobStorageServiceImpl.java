package org.project.speakeval.services.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import org.project.speakeval.exception.MissingFileException;
import org.project.speakeval.services.BlobStorageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

@Service
public class BlobStorageServiceImpl implements BlobStorageService {

    private final BlobContainerClient audioAnswersBlobContainerClient;

    public BlobStorageServiceImpl(@Qualifier(value = "audioAnswersBlobContainerClient") BlobContainerClient audioAnswersBlobContainerClient) {
        this.audioAnswersBlobContainerClient = audioAnswersBlobContainerClient;
    }

    @Override
    @Transactional
    public String upload(MultipartFile file,
                         String fileName,
                         String relatedEntityType,
                         String relatedEntityId) {
        if (file == null) {
            throw new MissingFileException("Missing file for " + relatedEntityType + " " + relatedEntityId);
        }

        BlobClient blobClient = audioAnswersBlobContainerClient.getBlobClient(fileName);

        try (InputStream in = file.getInputStream()) {
            blobClient.upload(in, file.getSize(), true);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to upload file for " + relatedEntityType + " " + relatedEntityId, e);
        }

        return blobClient.getBlobUrl();
    }
}
