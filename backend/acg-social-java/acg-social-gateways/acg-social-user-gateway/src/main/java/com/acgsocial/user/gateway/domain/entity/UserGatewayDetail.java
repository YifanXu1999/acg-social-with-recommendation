package com.acgsocial.user.gateway.domain.entity;

import com.acgsocial.user.gateway.domain.dto.SessionDetail;
import com.acgsocial.user.gateway.domain.vo.AuthTokenResponse;
import com.acgsocial.user.gateway.util.context.ApplicationContextProvider;
import com.acgsocial.user.gateway.util.session.SessionUtil;
import com.acgsocial.utils.jwt.JwtUtilService;
import io.jsonwebtoken.Claims;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Redis entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class UserGatewayDetail implements java.io.Serializable {
    // User id
    private Long id;
    private String userName;

    private List<SessionDetail> sessionDetails = new ArrayList<>();
    private TokenDetail accessTokenDetail;
    private TokenDetail refreshTokenDetail;





    public UserGatewayDetail(AuthTokenResponse authTokenResponse, SessionDetail sessionDetail ) {
        String accessToken = authTokenResponse.getAccessToken();
        String refreshToken = authTokenResponse.getRefreshToken();
        Claims accessclaims= jwtUtilService().extractAllClaims(accessToken);
        Claims refreshClaims = jwtUtilService().extractAllClaims(refreshToken);
        this.id = Long.valueOf(accessclaims.getSubject());
        this.userName = accessclaims.get("username", String.class);
        this.accessTokenDetail = new TokenDetail(accessToken);
        this.refreshTokenDetail = new TokenDetail(refreshToken);
        this.sessionDetails.add(sessionDetail);


    }




    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TokenDetail {
        private Date expireTime;
        private String token;

        private boolean isExpired() {
            return expireTime.after(new Date());
        }


        public TokenDetail (String token) {
            Claims claims = jwtUtilService().extractAllClaims(token);
            this.expireTime = claims.getExpiration();
            this.token = token;
        }
    }

    private static JwtUtilService jwtUtilService(){
        return ApplicationContextProvider.bean(JwtUtilService.class);
    }


}
