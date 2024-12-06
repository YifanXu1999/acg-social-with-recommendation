package com.acgsocial.user.gateway.route;


import com.acgsocial.common.result.ResponseResult;
import com.acgsocial.user.gateway.domain.dto.SessionDetail;
import com.acgsocial.user.gateway.domain.entity.UserGatewayDetail;
import com.acgsocial.user.gateway.domain.vo.AuthTokenResponse;
import com.acgsocial.user.gateway.util.redis.RedisKey;
import com.acgsocial.user.gateway.util.result.ResponseResultUtil;
import com.acgsocial.user.gateway.util.redis.RedisUtil;
import com.acgsocial.user.gateway.util.session.SessionUtil;
import com.acgsocial.utils.jwt.JwtUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtResponseRoute implements CustomizedRoute {




    private final RedisUtil redisUtil;

    private final JwtUtilService jwtUtil;
    private final SessionUtil sessionUtil;


    @Override
    public Function<PredicateSpec, Buildable<Route>>  getRouteFn() {

        return r -> r.path("/user-service/**")
          .filters(
            f -> f
              .rewritePath("/user-service/(?<remaining>.*)", "/${remaining}")
              .modifyResponseBody(ResponseResult.class, ResponseResult.class, rewriteFunction())
          )
          .uri("lb://user-service");
    }

    @Override
    public String getRouteId() {
        return "jwt-response-route";
    }

    private RewriteFunction<ResponseResult, ResponseResult> rewriteFunction() {

        return (exchange, resposnse) -> {
            /*
            1. Get the access token from the response
            2. Update the session, if session belongs to another user, please remove.
            3. Update session and token to user information
             */


            AuthTokenResponse tokenResponse = ResponseResultUtil.parse(resposnse, AuthTokenResponse.class);
            Long userId = Long.valueOf(jwtUtil.extractAllClaims(tokenResponse.getAccessToken()).getSubject());
            sessionUtil.setUserId(exchange, userId.toString());
            SessionDetail sessionDetail = sessionUtil.getSessionDetail(exchange);
            UserGatewayDetail userGatewayDetail = new UserGatewayDetail(tokenResponse, sessionDetail);

            redisUtil.setJson(RedisKey.USER_GATEWAY_DETAIL + userId, userGatewayDetail);
            // TODO Extract the user information from the token
            redisUtil.setHashMap(RedisKey.USER_SESSION + sessionDetail.getSessionId(), sessionDetail.getKeyValueList(), Duration.ofMinutes(30));
            exchange.getResponse().addCookie(org.springframework.http.ResponseCookie.from("cookieName", "cookieValue").build());
            return Mono.just(resposnse);
        };
    }




}
