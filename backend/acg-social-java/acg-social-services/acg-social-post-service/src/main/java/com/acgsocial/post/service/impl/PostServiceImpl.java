package com.acgsocial.post.service.impl;

import com.acgsocial.post.service.PostService;
import com.acgsocial.common.result.ResponseResult;
import com.acgsocial.utils.minio.MinioService;
import com.acgsocial.utils.minio.domain.FileMetaData;
import jakarta.validation.constraints.Null;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
    private final MinioService minioService;

    @Autowired
    public PostServiceImpl(MinioService minioService) {
        this.minioService = minioService;
    }


    @Override
    public ResponseResult<Void> addNewPost(MultipartFile file) {
        try {
            FileMetaData fileMetaData = minioService.uploadFile(file);
            log.info("File uploaded: " + fileMetaData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseResult.success(null);

    }

}
