package com.yifan.models.dto.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSignInDto {

    private String username;
    private String password;

}
