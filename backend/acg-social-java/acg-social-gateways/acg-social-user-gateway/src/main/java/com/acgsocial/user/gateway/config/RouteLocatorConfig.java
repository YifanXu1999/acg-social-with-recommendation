package com.acgsocial.user.gateway.config;

import com.acgsocial.user.gateway.route.CustomizedRoute;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Configuration
public class RouteLocatorConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {

        return WebClient.builder();
        /********/
    }

    @Bean
    public WebClient loadBalancedwebClient(WebClient.Builder loadBalancedWebClient) {
        return loadBalancedWebClient.build();
    }


    /*
        Important
        * This method builds the RouteLocator bean using the RouteLocatorBuilder and the CustomizedRoute beans.
     */
    @Bean
    public RouteLocator buildRouteLocator(RouteLocatorBuilder builder, List<CustomizedRoute> routes) {
        RouteLocatorBuilder.Builder routeBuilder = builder.routes();
        routes.stream().forEach(route -> {
            routeBuilder.route(route.getRouteId(), route.getRouteFn());
        });
        return routeBuilder.build();

    }

    @Bean RouterValidator routerValidator() {
        return new RouterValidator();
    }

    public class RouterValidator {

        public boolean isByPassPath(String path) {
            if (path.contains("login") || path.contains("register")) {
                return true;
            }
            return false;
        }
        public void validate() {
            // Validate the routes
        }
        public boolean test(String routeId) {
            // Validate the route
            return true;
        }
    }
}
