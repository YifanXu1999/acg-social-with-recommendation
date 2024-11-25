package com.acgsocial.post.service.impl;

import com.acgsocial.post.service.PostService;
import com.acgsocial.common.result.ResponseResult;
import com.acgsocial.utils.minio.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
public class PostServiceImpl implements PostService {
    private final MinioService minioService;

    @Autowired
    public PostServiceImpl(MinioService minioService) {
        this.minioService = minioService;
    }

    @Value("${file.upload-dir}")
    private String UPLOAD_DIR;
    @Override
    public ResponseResult upload(MultipartFile file) {
        // Download the file to  UPLOAD_DIR
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        System.out.println("File uploaded: " + file.getOriginalFilename());
        File destinationFile = new File(UPLOAD_DIR, Objects.requireNonNull(file.getOriginalFilename()));
        // Save the file locally
        try {
            file.transferTo(destinationFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Save the file info to database
        // Save  the tmp file path to redis cache
        // Notify the MQ service to process the file
        return null;
    }

    @Override
    public ResponseResult addNewPost(MultipartFile file) {
        try {
            minioService.uploadFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseResult.success(null);

    }

}
