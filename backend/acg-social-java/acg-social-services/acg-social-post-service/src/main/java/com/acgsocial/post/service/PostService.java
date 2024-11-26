package com.acgsocial.post.service;

import com.acgsocial.common.result.ResponseResult;
import com.acgsocial.post.domain.vo.PostVo;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    ResponseResult<Void> addNewPost(PostVo file);
}
