package com.acgsocial.user.domain.dto;

import com.acgsocial.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class AccountConnectRequest {
    private String provider;
    private String providerId;
    private User user;
}
