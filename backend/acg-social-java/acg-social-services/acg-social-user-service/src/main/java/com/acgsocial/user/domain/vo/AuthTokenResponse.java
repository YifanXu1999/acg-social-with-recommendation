package com.acgsocial.user.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthTokenResponse {
    private String accessToken;
    private String refreshToken;
}
