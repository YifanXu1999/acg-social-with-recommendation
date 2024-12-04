package com.acgsocial.user.gateway.domain.dao;

import java.time.LocalDateTime;
import java.util.List;

// Redis entity
public class UserGatewayDetail {
    // User id
    private Long id;
    private List<SessionDetail> sessionDetails;
    private TokenDetail accessTokenDetail;
    private TokenDetail refreshTokenDetail;


    
    private class TokenDetail {
        private String token;
        private LocalDateTime expireTime;
        private boolean isExpired() {
            return expireTime.isAfter(LocalDateTime.now());
        }
    }

    private class SessionDetail {
        private String sessionId;
        private LocalDateTime createdTime;
        private Double maxDuration;

    }


}
