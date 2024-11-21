package com.yifan.models.pojo.post;

import com.yifan.models.pojo.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "post_file")
@Getter
@Setter
public class PostFile  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "url")
    private String url;

}
