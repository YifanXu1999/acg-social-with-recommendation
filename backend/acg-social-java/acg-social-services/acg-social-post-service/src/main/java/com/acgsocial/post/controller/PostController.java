package com.acgsocial.post.controller;

import com.acgsocial.post.service.PostService;
import com.acgsocial.common.result.ResponseResult;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {


    private PostService fileService;

    @PostMapping("/new")
    public ResponseResult addNewPost(@RequestBody MultipartFile file) throws IOException {
        return fileService.addNewPost(file);
    }


}
