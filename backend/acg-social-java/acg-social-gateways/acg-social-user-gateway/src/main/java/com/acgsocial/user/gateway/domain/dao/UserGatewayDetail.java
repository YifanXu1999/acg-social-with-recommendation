package com.acgsocial.user.gateway.domain.dao;

import java.util.List;

// Redis entity
public class UserGatewayDetail {
    // User id
    private Long id;
    private List<String> sessionIds;
    private TokenDetail accessTokenDetail;
    private TokenDetail refreshTokenDetail;
}
