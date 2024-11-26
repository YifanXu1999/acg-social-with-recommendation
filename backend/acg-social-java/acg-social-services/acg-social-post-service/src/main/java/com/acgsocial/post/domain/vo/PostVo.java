package com.acgsocial.post.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class PostVo {
    private String title;
    private List<PostCoverVo> coverList;
    private MultipartFile content;

}
