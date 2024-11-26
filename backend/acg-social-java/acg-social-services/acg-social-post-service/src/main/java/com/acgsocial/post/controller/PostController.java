package com.acgsocial.post.controller;

import com.acgsocial.post.domain.vo.PostCoverVo;
import com.acgsocial.post.domain.vo.PostMetaDataVo;
import com.acgsocial.post.domain.vo.PostVo;
import com.acgsocial.post.service.PostService;
import com.acgsocial.common.result.ResponseResult;

import com.acgsocial.utils.json.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


@RestController
@RequestMapping("/post")
@AllArgsConstructor
@Tag(name = "Post Controller", description = "Controller for managing posts")
public class PostController {

    private PostService fileService;

    @PostMapping("/new")
    public ResponseResult<Void> addNewPost(
      @RequestPart("covers") List<MultipartFile> covers,
      @RequestPart("content") MultipartFile content,
      @RequestPart("metaData") String metaData
    ) throws IOException {
        PostMetaDataVo metaDataVo = JsonUtil.parse(metaData, PostMetaDataVo.class);
        List<PostCoverVo> coverVoList =  new ArrayList<>();
        // loop through covers and get index stream
        IntStream.range(0, covers.size()).forEach(i -> {
            PostCoverVo postCoverVo = new PostCoverVo(metaDataVo.getCoverOrderList().get(i),
                                                                    covers.get(i),
                                                                    metaDataVo.getCoverStatusList().get(i));
            coverVoList.add(postCoverVo);
        });
        PostVo postVo = new PostVo(metaDataVo.getTitle(), coverVoList, content);
        return fileService.addNewPost(postVo);
    }
}