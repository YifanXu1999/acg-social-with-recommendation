package com.acgsocial.models.pojo.user;

import com.acgsocial.models.pojo.BaseEntity;
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
public class UserDelete extends BaseEntity {
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