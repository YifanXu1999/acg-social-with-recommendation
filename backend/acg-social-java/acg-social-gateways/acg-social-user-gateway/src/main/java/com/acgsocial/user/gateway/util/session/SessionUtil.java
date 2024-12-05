package com.acgsocial.user.gateway.util.session;

import com.acgsocial.user.gateway.domain.entity.UserGatewayDetail;
import com.acgsocial.user.gateway.domain.dto.SessionDetail;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SessionUtil {

    @Autowired
    private RedissonClient redissonClient;

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
        SessionDetail sessionDetail = new SessionDetail();
        exchange.getSession().subscribe(
                session -> {
                    if (!session.isStarted()) {
                        session.start();
                    }
                    sessionDetail.setSessionId(session.getId());
                    System.out.println("1000000\n\n\n" +session.getId());
                    sessionDetail.setUserId(session.getAttributeOrDefault("userId", null));
                }
        );
        System.out.println(sessionDetail.getSessionId());
        return sessionDetail;
    }

    public UserGatewayDetail getUserGatewayDetail(SessionDetail sessionDetail) {
        // Retrieve the user information from the session
        String userId = sessionDetail.getUserId();
        AtomicReference<UserGatewayDetail> userGatewayDetail = new AtomicReference<>();
        Optional.ofNullable(userId).ifPresent(
          id -> {
              JacksonCodec<UserGatewayDetail> codec = new JacksonCodec<>(UserGatewayDetail.class);
              userGatewayDetail.set((UserGatewayDetail) redissonClient.getJsonBucket("user_gateway_detail:" + id, codec).get());

          }
        );
        return userGatewayDetail.get();
    }
}
