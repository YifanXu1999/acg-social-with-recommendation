package com.acgsocial.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class EmailSignUpRequest {
    private String email;
    private String password;
}
