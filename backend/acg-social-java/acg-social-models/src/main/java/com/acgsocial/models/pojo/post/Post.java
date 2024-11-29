package com.acgsocial.models.pojo.post;

import com.acgsocial.models.pojo.BaseEntity;
import com.acgsocial.models.pojo.user.UserDelete;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    private UserDelete user;

    @Column
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @OneToOne
    @JoinColumn(name = "content_id")
    private PostContent content;




}
