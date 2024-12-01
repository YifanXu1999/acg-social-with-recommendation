package com.acgsocial.user.domain.dto;


import lombok.*;

@Builder
@Getter
@ToString
public class Oauth2SignUpRequest {
    private String provider;
    private Long providerId;
    private String email;
    private String avatarUrl;
}
