package com.acgsocial.user.gateway.route;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;

import java.util.function.Function;

public interface CustomizedRoute {
    public Function<PredicateSpec, Buildable<Route>>  getRouteFn();
    public String getRouteId();
}
