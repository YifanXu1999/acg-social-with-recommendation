package com.acgsocial.user.gateway.filter;

import com.acgsocial.user.gateway.config.RouteLocatorConfig;
import com.acgsocial.user.gateway.domain.dao.UserGatewayDetail;
import com.acgsocial.user.gateway.domain.dto.SessionDetail;
import com.acgsocial.user.gateway.util.session.SessionUtil;
import com.acgsocial.utils.jwt.JwtUtilService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JacksonCodec;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RefreshScope
@Component
@Order(-1)
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter {

    private final RouteLocatorConfig.RouterValidator routerValidator;
    private final JwtUtilService jwtUtil;
    private final RedissonClient redissonClient;



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // Get Session Detail (start session if not started)
        SessionDetail sessionDetail = SessionUtil.getSessionDetail(exchange);
        // Check if the request can be sent without authentication
        if (routerValidator.isByPassPath(request.getPath().value())) {
            return chain.filter(exchange);
        }
        // Retrieve the user information from the session
        JacksonCodec<UserGatewayDetail> codec = new JacksonCodec<>(UserGatewayDetail.class);
        redissonClient.getJsonBucket("usergatewaydetail:" + sessionDetail.getSessionId(), codec).set(UserGatewayDetail.builder().userId(
          "abc").build());
        UserGatewayDetail userGatewayDetail = this.getUserGatewayDetail(sessionDetail);

        // Check if the request has a valid token


        if (routerValidator.test(request.getPath().value())) {
            if (this.isAuthMissing(request)) {
                return this.onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            final String token = this.getAuthHeader(request);

            if (jwtUtil.isTokenExpired(token)) {
                return this.onError(exchange, HttpStatus.FORBIDDEN);
            }

            this.updateRequest(exchange, token);
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private void updateRequest(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.extractAllClaims(token);
        exchange.getRequest().mutate()
                .header("email", String.valueOf(claims.get("email")))
                .build();
    }

    private UserGatewayDetail getUserGatewayDetail(SessionDetail sessionDetail) {
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