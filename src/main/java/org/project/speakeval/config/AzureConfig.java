package org.project.speakeval.config;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureConfig {

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    @Value("${azure.storage.containers.audio-answers}")
    private String audioAnswersContainer;

    @Value("${azure.storage.containers.question-assets}")
    private String questionAssetsContainer;

    @Value("${azure.storage.containers.user-profiles}")
    private String userProfilesContainer;

    @Bean(name = "audioAnswersBlobContainerClient")
    public BlobContainerClient audioAnswersBlobContainerClient() {
        return new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient()
                .getBlobContainerClient(audioAnswersContainer);
    }

    @Bean(name = "questionAssetsBlobContainerClient")
    public BlobContainerClient questionAssetsBlobContainerClient() {
        return new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient()
                .getBlobContainerClient(questionAssetsContainer);
    }

    @Bean(name = "userProfilesBlobContainerClient")
    public BlobContainerClient userProfilesBlobContainerClient() {
        return new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient()
                .getBlobContainerClient(userProfilesContainer);
    }
}
