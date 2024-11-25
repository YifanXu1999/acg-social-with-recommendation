package com.acgsocial.utils.minio;

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class MinioService {

    private final CustomMinioClient minioClient;

    @Autowired
    public MinioService(CustomMinioClient minioClient) {
        this.minioClient = minioClient;
    }


    public String uploadFile(MultipartFile file) throws Exception {
        return minioClient.uploadFile(file);
    }








}
