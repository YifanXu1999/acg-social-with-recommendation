package com.acgsocial.user.gateway.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class AuthTokenResponse implements Serializable {
    private String accessToken;
    private String refreshToken;
}
