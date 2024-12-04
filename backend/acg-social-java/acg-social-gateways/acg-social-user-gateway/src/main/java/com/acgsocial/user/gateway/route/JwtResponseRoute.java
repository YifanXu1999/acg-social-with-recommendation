package com.acgsocial.user.gateway.route;

import com.acgsocial.common.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.function.Function;

@Component
public class JwtResponseRoute implements CustomizedRoute {

    @Autowired
    private WebClient webClient;

    public JwtResponseRoute(WebClient webClient) {
        this.webClient = webClient;
    }

    public Function<PredicateSpec, Buildable<Route>>  getRouteFn() {

        return r -> r.path("/user-service/**")
          .filters(
            f -> f.rewritePath("/user-service/(?<remaining>.*)", "/${remaining}")
              .modifyResponseBody(ResponseResult.class, ResponseResult.class,
                (exchange, s) ->  {
                    exchange.getSession().subscribe(session -> {
                        if(! session.isStarted()) {
                            session.start();
                            webClient.get().uri(
                                "http://user-service/hello/a"
                              )
                              .retrieve()
                              .bodyToMono(String.class)
                              .subscribe(response -> {
                                  System.out.println("Response: " + response);
                              });
                            session.getAttributes().put("user",
                              s.getData() + session.getId() + LocalDateTime.now() + session.getMaxIdleTime());
                        }
                        s.setData(session.getAttribute("user"));
                    });
                    exchange.getResponse().addCookie(org.springframework.http.ResponseCookie.from("cookieName", "cookieValue").build());
                    return Mono.just(s);
                }))
          .uri("lb://user-service");
    }

    public String getRouteId() {
        return "jwt-response-route";
    }




}
