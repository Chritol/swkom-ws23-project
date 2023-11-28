package org.openapitools.paperlessocr.configuration;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MinioConfiguration {
    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.user}")
    private String user;

    @Value("${minio.password}")
    private String password;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Bean
    public MinioClient minioClient() {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(user, password)
                .build();

        createBucketIfNotExists(minioClient);

        return minioClient;
    }

    private void createBucketIfNotExists(MinioClient minioClient) {
        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                log.info("MinIO - Created bucket with name: " + bucketName);
            } else {
                log.info("MinIO - Bucket with name found: " + bucketName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Bucket creation failed", e);
        }
    }
}
