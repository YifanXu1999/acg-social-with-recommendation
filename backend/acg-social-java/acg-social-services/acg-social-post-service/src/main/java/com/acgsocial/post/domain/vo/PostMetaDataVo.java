package com.acgsocial.post.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostMetaDataVo  implements Serializable {
    private String title;

    private List<Boolean> coverStatusList;

    private List<Integer> coverOrderList;

    // Check if cover is new;
//    private List<Boolean>  coverStatusList;



}
