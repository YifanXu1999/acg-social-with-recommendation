package com.acgsocial.user.domain.dto;

import com.acgsocial.user.domain.enums.Oauth2ProviderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Oauth2LoginRequest {
    private Oauth2ProviderEnum provider;
    private String code;
}
