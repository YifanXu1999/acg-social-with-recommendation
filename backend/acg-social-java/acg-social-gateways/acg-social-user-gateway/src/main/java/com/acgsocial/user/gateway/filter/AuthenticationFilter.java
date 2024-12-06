package com.acgsocial.user.gateway.filter;

import com.acgsocial.user.gateway.config.RouteLocatorConfig;
import com.acgsocial.user.gateway.domain.entity.UserGatewayDetail;
import com.acgsocial.user.gateway.domain.dto.SessionDetail;
import com.acgsocial.user.gateway.util.session.SessionUtil;
import com.acgsocial.utils.jwt.JwtUtilService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RefreshScope
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter {

    private final RouteLocatorConfig.RouterValidator routerValidator;
    private final JwtUtilService jwtUtil;
    private final RedissonClient redissonClient;
    private final SessionUtil sessionUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        // Init session and retrive detail from redis
        SessionDetail sessionDetail = sessionUtil.initSession(exchange);

        // Retrieve the user information from the session
        UserGatewayDetail userGatewayDetail = sessionUtil.getUserGatewayDetail(sessionDetail);


        if (routerValidator.isByPassPath(request.getPath().value())) {
            return chain.filter(exchange);
        }
        // Check if the request has a valid token
        if(! routerValidator.isAuthenticated(userGatewayDetail)) {
            return this.onError(exchange, HttpStatus.UNAUTHORIZED);
        }

        // If authenticated, update the request with the user information
        // TODO: Insert access token and refresh token to the header
        exchange = updateRequestAuthHeaders(exchange, userGatewayDetail.getAccessTokenDetail().getToken());

        return chain.filter(exchange);

    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }


    private ServerWebExchange updateRequestAuthHeaders(ServerWebExchange exchange, String token) {
        ServerHttpRequest request = exchange.getRequest().mutate().headers((httpHeaders) -> {;
            String csrf = new HttpCookie("csrf", "3838a26d-07f7-11e9-b5f7").toString();
            String ssn = new HttpCookie("ssn", "MTU0NT").toString();
            String auth = new HttpCookie("Access-Token",  token).toString();
            httpHeaders.set("Cookie", csrf+";"+ssn + ";" + auth);

        }).build();

        return exchange.mutate().request(request).build();
    }



}