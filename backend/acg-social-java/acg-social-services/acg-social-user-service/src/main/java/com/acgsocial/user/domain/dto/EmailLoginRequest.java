package com.acgsocial.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailLoginRequest {
    private String email;
    private String password;
}
