package com.acgsocial.user.gateway.route;

import com.acgsocial.common.result.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Component
public class UserServiceRoute implements CustomizedRoute{

    @Override
    public Function<PredicateSpec, Buildable<Route>> getRouteFn() {
        return r -> r.path("/user-service/**")
          .filters(
            f -> f
              .rewritePath("/user-service/(?<remaining>.*)", "/${remaining}")
          )
          .uri("lb://user-service");
    }

    @Override
    public String getRouteId() {
        return "user-service-route";
    }
}
