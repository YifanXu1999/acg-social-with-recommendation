package com.yifan.models.pojo.user;

import com.yifan.models.pojo.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_info")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String phone;



}
