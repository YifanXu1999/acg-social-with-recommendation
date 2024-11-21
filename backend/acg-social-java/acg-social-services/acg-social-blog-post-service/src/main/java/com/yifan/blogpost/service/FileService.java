package com.yifan.blogpost.service;

import com.yifan.common.result.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    ResponseResult upload(MultipartFile file);
}
