package com.yifan.user.gateway.route;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("get_route", r -> r.path("/user-service/**")
                        .filters(f -> f.rewritePath("/user-service/(?<remaining>.*)", "/user/${remaining}"))
                        .uri("lb://user-service")
                )
                .build();
    }
}
