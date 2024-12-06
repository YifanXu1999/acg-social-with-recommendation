package com.acgsocial.user.gateway.util.session;

import com.acgsocial.user.gateway.domain.entity.UserGatewayDetail;
import com.acgsocial.user.gateway.domain.dto.SessionDetail;
import com.acgsocial.user.gateway.util.redis.RedisKey;
import com.acgsocial.user.gateway.util.redis.RedisUtil;
import org.apache.http.client.protocol.RequestAddCookies;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SessionUtil {

    @Autowired
    private RedissonClient redissonClient;

    private final String SESSION_NAME = "ACG_SESSIONID";

    @Autowired
    private RedisUtil redisUtil;

    public SessionDetail initSession(ServerWebExchange exchange) {
        // Check if request has cookie of SESSION_NAME, if nto create a new one

        boolean hasSessionCookie = exchange.getRequest()
          .getCookies()
          .getFirst(SESSION_NAME) != null;

        String sessionID = hasSessionCookie ? exchange.getRequest().getCookies().getFirst(SESSION_NAME).getValue() : generateNewSessionId();
        // Check if sessionID exists in the redis
        RMap sessionDetailMap = redisUtil.getHashMap(RedisKey.USER_SESSION + sessionID);
        SessionDetail sessionDetail;
        if (sessionDetailMap == null || sessionDetailMap.isEmpty()) {
            sessionDetail = new SessionDetail(sessionID);
            redisUtil.setHashMap(RedisKey.USER_SESSION + sessionID, sessionDetail.getKeyValueList(), Duration.ofMinutes(30));
            sessionDetailMap = redisUtil.getHashMap(RedisKey.USER_SESSION + sessionID);
        } else {
            sessionDetail = new SessionDetail(sessionDetailMap);
        }


        // Refresh the session cookie of the response
        ResponseCookie sessionCookie = ResponseCookie.from(SESSION_NAME, sessionID)
          .path("/")
          .httpOnly(true) // Prevents client-side scripts from accessing the cookie
          .secure(false) // Set to true if using HTTPS
          .maxAge(3600) // 1 hour expiration
          .build();

        exchange.getResponse().addCookie(sessionCookie);
        return sessionDetail;
    }


    public void setUserId(ServerWebExchange exchange, String userId) {
        exchange.getSession().subscribe(
                session -> {
                    if (!session.isStarted()) {
                        session.start();
                    }
                    session.getAttributes().put("userId", userId);
                }
        );
    }

    public  SessionDetail getSessionDetail(ServerWebExchange exchange) {
        // Find session detail by session id
        String sessionId = exchange.getResponse().getCookies().get(SESSION_NAME).getFirst().getValue();
        RMap<String, Object> sessionMap = redisUtil.getHashMap(RedisKey.USER_SESSION + sessionId);
        // Build SessionDetail object
        SessionDetail sessionDetail = new SessionDetail(sessionMap);
        return sessionDetail;
    }

    public UserGatewayDetail getUserGatewayDetail(SessionDetail sessionDetail) {
        // Retrieve the user information from the session


        Long userId = sessionDetail.getUserId();
        AtomicReference<UserGatewayDetail> userGatewayDetail = new AtomicReference<>();
        Optional.ofNullable(userId).ifPresent(
          id -> {
              JacksonCodec<UserGatewayDetail> codec = new JacksonCodec<>(UserGatewayDetail.class);
              System.out.println(redissonClient.getJsonBucket("user_gateway_detail:" + id, codec).get());
              userGatewayDetail.set((UserGatewayDetail) redissonClient.getJsonBucket("user_gateway_detail:" + id, codec).get());

          }
        );
        return userGatewayDetail.get();
    }

    private String generateNewSessionId() {
        // Generate a unique session ID (you can replace this with a more robust implementation)
        return java.util.UUID.randomUUID().toString();
    }

}
