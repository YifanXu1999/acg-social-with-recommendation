package com.yifan.blogpost.controller;

import com.yifan.blogpost.service.FileService;
import com.yifan.common.result.ResponseResult;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
