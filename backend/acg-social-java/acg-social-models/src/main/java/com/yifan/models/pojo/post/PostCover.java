package com.yifan.models.pojo.post;

import com.yifan.models.pojo.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "post_cover")
@Getter
@Setter
public class PostCover {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="cover_order", nullable = false)
    private Integer order;

    @OneToOne
    @JoinColumn(name = "file_id", nullable = false)
    private PostFile file;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

}
