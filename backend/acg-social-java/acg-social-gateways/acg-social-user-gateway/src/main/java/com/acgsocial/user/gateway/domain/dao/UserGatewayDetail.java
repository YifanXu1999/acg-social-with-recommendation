package com.acgsocial.user.gateway.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

// Redis entity
@Getter
@Builder
@AllArgsConstructor
@Setter
public class UserGatewayDetail implements java.io.Serializable {
    // User id
    private Long id;
    private String userId;

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
