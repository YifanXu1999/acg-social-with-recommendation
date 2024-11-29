package com.acgsocial.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Oauth2AccountQueryRequest {
    private String provider;
    private String providerId;
}
