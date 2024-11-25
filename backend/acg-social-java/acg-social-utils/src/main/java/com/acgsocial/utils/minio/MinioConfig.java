package com.acgsocial.utils.minio;

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MinioConfig {

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Bean
    public CustomMinioClient customMinioClient() throws MinioException {
        MinioClient minioClient = MinioClient.builder()
          .endpoint(endpoint)
          .credentials(accessKey, secretKey)
          .build();
        CustomMinioClient customMinioClient = new CustomMinioClient(minioClient);
        customMinioClient.loadBucketName(bucketName);
        return customMinioClient;
    }


}
