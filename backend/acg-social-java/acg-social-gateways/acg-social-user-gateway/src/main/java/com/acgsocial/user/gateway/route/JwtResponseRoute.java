package com.acgsocial.user.gateway.route;


import com.acgsocial.common.result.ResponseResult;
import com.acgsocial.user.gateway.domain.dto.SessionDetail;
import com.acgsocial.user.gateway.domain.vo.AuthTokenResponse;
import com.acgsocial.user.gateway.util.ResponseResult.ResponseResultUtil;
import com.acgsocial.user.gateway.util.redis.RedisKey;
import com.acgsocial.user.gateway.util.redis.RedisUtil;
import com.acgsocial.user.gateway.util.session.SessionUtil;
import com.acgsocial.utils.json.JsonUtil;
import com.acgsocial.utils.jwt.JwtUtilService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtResponseRoute implements CustomizedRoute {


    private final  WebClient webClient;


    private final RedisUtil redisUtil;

    private final JwtUtilService jwtUtil;





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

            SessionDetail sessionId = SessionUtil.getSessionDetail(exchange);

            AuthTokenResponse tokenResponse = ResponseResultUtil.parse(resposnse, AuthTokenResponse.class);

            // TODO Extract the user information from the token
            jwtUtil.extractUsername(tokenResponse.getAccessToken());



            exchange.getSession().subscribe(session -> {
                if(! session.isStarted()) {
                    session.start();
//                    webClient.get().uri(
//                        "http://user-service/hello/a"
//                      )
//                      .retrieve()
//                      .bodyToMono(String.class)
//                      .subscribe(response -> {
//                          System.out.println("Response: " + response);
//                      });
                    session.getAttributes().put("user",
                      resposnse.getData() + session.getId() + LocalDateTime.now() + session.getMaxIdleTime());
                }



                resposnse.setData(session.getAttribute("user"));
            });
            exchange.getResponse().addCookie(org.springframework.http.ResponseCookie.from("cookieName", "cookieValue").build());
            return Mono.just(resposnse);
        };
    }




}
