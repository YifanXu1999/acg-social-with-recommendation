package com.acgsocial.post.service.impl;

import com.acgsocial.post.domain.vo.PostVo;
import com.acgsocial.post.filter.RequestScopedBean;
import com.acgsocial.post.service.PostService;
import com.acgsocial.common.result.ResponseResult;
import com.acgsocial.utils.minio.MinioService;
import com.acgsocial.utils.minio.domain.FileMetaData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
    private final MinioService minioService;
    private final RequestScopedBean requestScopedBean;


    @Autowired
    public PostServiceImpl(MinioService minioService, RequestScopedBean requestScopedBean) {
        this.minioService = minioService;
        this.requestScopedBean = requestScopedBean;
    }


    @Override
    public ResponseResult<Void> addNewPost(PostVo postVo) {
        /*
        1. Upload the post cover to Minio
        2. Upload the post content to Minio
        3. Wait for minio uploads to be finished
        4. Send message to mq
         */

        // Upload the post covers to Minio
        List<CompletableFuture<FileMetaData>>  uploadCoverFutures
            = postVo.getCoverList()
                        .stream()
                        .map(postCoverVo -> uploadPostToMinio(postCoverVo.getCover()))
                        .toList();

        // Upload the post content to Minio
        CompletableFuture<FileMetaData> uploadContentFuture = uploadPostToMinio(postVo.getContent());

        // Join all the futures
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
            Stream
                .concat(Stream.of(uploadContentFuture), uploadCoverFutures.stream())
                .toArray(CompletableFuture[]::new)
        );



        // Wait for minio uploads to be finished
            allFutures.join();

        uploadCoverFutures.stream().map(CompletableFuture::join).forEach(fileMetaData -> {
            log.info("Uploaded file: {}", fileMetaData);
        });

        uploadContentFuture.thenAccept(fileMetaData -> {
            log.info("Uploaded file: {}", fileMetaData);
        });

        log.info("All files uploaded successfully");
        // Send message to mq
        // Add your message queue logic here


        return ResponseResult.success(null);

    }

    private CompletableFuture<FileMetaData> uploadPostToMinio(MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                FileMetaData fileMetaData = minioService.uploadFile(file);
                log.info("Uploaded file: {}", fileMetaData);
                return fileMetaData;
            } catch (Exception e) {
                log.error("Failed to upload file", e);
                throw new RuntimeException(e);
            }
        });
    }

}
