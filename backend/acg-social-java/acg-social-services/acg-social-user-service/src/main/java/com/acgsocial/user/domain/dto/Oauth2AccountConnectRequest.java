package com.acgsocial.user.domain.dto;

import com.acgsocial.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Oauth2AccountConnectRequest {
    private String provider;
    private String providerId;
    private User user;
}
