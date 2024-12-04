package com.acgsocial.user.gateway.domain.dao;

import java.time.LocalDateTime;

public class TokenDetail {
    private String token;
    private LocalDateTime expireTime;

    private boolean isExpired() {
        return expireTime.isAfter(LocalDateTime.now());
    }
}
