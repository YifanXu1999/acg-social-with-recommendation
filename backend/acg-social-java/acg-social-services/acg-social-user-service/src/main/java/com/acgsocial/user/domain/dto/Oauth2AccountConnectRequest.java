package com.acgsocial.user.domain.dto;

import com.acgsocial.user.domain.dao.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Oauth2AccountConnectRequest {
    private String provider;
    private Long providerId;
    private User user;
}
