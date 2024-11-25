package com.acgsocial.utils.minio;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CustomMinioClient {

    private final MinioClient minioClient;

    private  List<String> bucketNames;

    public CustomMinioClient(MinioClient minioClient) {
        this.minioClient = minioClient;
        this.bucketNames = new ArrayList<>();

    }

    public String uploadFile(MultipartFile file) throws MinioException {
        try {
            InputStream fileStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            fileName = fileName.substring(0, fileName.lastIndexOf('.'))
                                    + "-"
                                    + java.util.UUID.randomUUID()
                                    +  fileName.substring(fileName.lastIndexOf('.')) ;
            minioClient.putObject(PutObjectArgs
                .builder()
                .bucket(bucketNames.get(0))
                .object(fileName)
                .stream(fileStream, file.getSize(), -1)
                .build());
            return fileName;
        } catch (Exception e) {
            throw new MinioException("Error uploading file", e.getMessage());
        }
    }


    public void loadBucketName(String bucketName) throws MinioException {
        createBucket(bucketName);
        this.bucketNames.add(bucketName);
    }





    private void createBucket(String bucketName) throws MinioException {

        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            throw new MinioException("Error creating bucket", e.getMessage());
        }
    }



}
