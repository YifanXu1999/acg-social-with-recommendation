package com.yifan.blogpost.service.controller;

import com.yifan.common.result.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    private static final String UPLOAD_DIR = "/Users/yifanxu/Projects/acg-social-with-recommendation/backend/acg" +
      "-social-java/tmp/uploads/";

    @PostMapping("/upload")
    public ResponseResult upload(@RequestBody MultipartFile file) throws IOException {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        System.out.println("File uploaded: " + file.getOriginalFilename());
        File destinationFile = new File(UPLOAD_DIR,  file.getOriginalFilename());

        // Save the file locally
        file.transferTo(destinationFile);
        return null;
    }
}
