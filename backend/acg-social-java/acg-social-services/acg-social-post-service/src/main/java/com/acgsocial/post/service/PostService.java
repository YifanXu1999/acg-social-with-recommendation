package com.acgsocial.post.service;

import com.acgsocial.common.result.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    ResponseResult upload(MultipartFile file);
    ResponseResult addNewPost(MultipartFile file);
}
