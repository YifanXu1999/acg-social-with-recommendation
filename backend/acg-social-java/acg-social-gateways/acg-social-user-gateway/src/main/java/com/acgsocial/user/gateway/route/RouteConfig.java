package com.acgsocial.user.gateway.route;

import com.acgsocial.common.result.ResponseResult;
import com.ecwid.consul.v1.Response;
import org.redisson.api.RedissonClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Configuration
public class RouteConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClient() {

        return WebClient.builder();
        /********/
    }


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, RedissonClient redisson,
                                           WebClient.Builder loadBalancedWebClient) {

        WebClient webClient = loadBalancedWebClient.build();
        return builder.routes()
                .route("get_route", r -> r.path("/user-service/**")
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
                                  return Mono.just(s);
                              }))
                        .uri("lb://user-service")
                )
              .build();
    }
}
