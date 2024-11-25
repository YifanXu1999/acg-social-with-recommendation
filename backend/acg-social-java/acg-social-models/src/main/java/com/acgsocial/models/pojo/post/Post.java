package com.acgsocial.models.pojo.post;

import com.acgsocial.models.pojo.BaseEntity;
import com.acgsocial.models.pojo.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "post")
@Getter
@Setter
@ToString
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    private String title;

    @OneToOne
    @JoinColumn(name = "content_id")
    private PostContent content;


}
