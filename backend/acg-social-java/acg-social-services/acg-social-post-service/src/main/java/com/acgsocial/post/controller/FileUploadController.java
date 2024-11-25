package com.acgsocial.post.controller;

import com.acgsocial.post.service.FileService;
import com.acgsocial.common.result.ResponseResult;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileUploadController {


    private FileService fileService;

    @PostMapping("/upload")
    public ResponseResult upload(@RequestBody MultipartFile file) throws IOException {
        return fileService.upload(file);
    }


}
