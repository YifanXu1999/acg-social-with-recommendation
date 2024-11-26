package com.acgsocial.post.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@ToString
@AllArgsConstructor
public class PostCoverVo {
    private Integer order;
    private MultipartFile cover;
    private boolean isSaved;
}
