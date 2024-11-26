package com.acgsocial.post.service;

import com.acgsocial.common.result.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    ResponseResult<Void> addNewPost(MultipartFile file);
}
