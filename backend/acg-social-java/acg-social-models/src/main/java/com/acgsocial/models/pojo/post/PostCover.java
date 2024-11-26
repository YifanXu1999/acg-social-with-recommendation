package com.acgsocial.models.pojo.post;

import com.acgsocial.models.pojo.MediaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "post_cover")
@Getter
@Setter
public class PostCover extends MediaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="cover_order", nullable = false)
    private Integer order;


    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

}
