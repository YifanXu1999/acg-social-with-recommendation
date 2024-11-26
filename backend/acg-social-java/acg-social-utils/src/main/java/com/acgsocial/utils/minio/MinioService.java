package com.acgsocial.utils.minio;

import com.acgsocial.utils.minio.domain.FileMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@ConditionalOnBean(CustomMinioClient.class)
public class MinioService {

    private final CustomMinioClient minioClient;

    @Autowired
    public MinioService(CustomMinioClient minioClient) {
        this.minioClient = minioClient;
    }


    public FileMetaData uploadFile(MultipartFile file) throws Exception {
        return minioClient.uploadFile(file);
    }








}
