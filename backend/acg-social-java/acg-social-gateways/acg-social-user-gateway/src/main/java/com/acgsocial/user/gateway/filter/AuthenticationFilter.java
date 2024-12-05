package com.acgsocial.user.gateway.filter;

import com.acgsocial.user.gateway.config.RouteLocatorConfig;
import com.acgsocial.user.gateway.domain.entity.UserGatewayDetail;
import com.acgsocial.user.gateway.domain.dto.SessionDetail;
import com.acgsocial.user.gateway.util.session.SessionUtil;
import com.acgsocial.utils.jwt.JwtUtilService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
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

@RefreshScope
@Component
@Order(-1)
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter {

    private final RouteLocatorConfig.RouterValidator routerValidator;
    private final JwtUtilService jwtUtil;
    private final RedissonClient redissonClient;
    private final SessionUtil sessionUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // Get Session Detail (start session if not started)
        SessionDetail sessionDetail = sessionUtil.getSessionDetail(exchange);
        // Check if the request can be sent without authentication
        if (routerValidator.isByPassPath(request.getPath().value())) {
            return chain.filter(exchange);
        }
        // Retrieve the user information from the session
        UserGatewayDetail userGatewayDetail = sessionUtil.getUserGatewayDetail(sessionDetail);

        // Check if the request has a valid token
        if(! routerValidator.isAuthenticated(userGatewayDetail)) {
            return this.onError(exchange, HttpStatus.UNAUTHORIZED);
        }

        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }


    private void updateRequest(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.extractAllClaims(token);
        exchange.getRequest().mutate()
                .header("email", String.valueOf(claims.get("email")))
                .build();
    }



}