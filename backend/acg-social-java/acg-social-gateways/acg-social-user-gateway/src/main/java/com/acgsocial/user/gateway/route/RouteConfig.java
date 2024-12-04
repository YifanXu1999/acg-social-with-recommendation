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
    public WebClient loadBalancedWebClient() {
        /********
         * Working Case
         * reactor.core.Exceptions$ErrorCallbackNotImplemented: org.springframework.web.reactive.function.client.WebClientRequestException: Failed to resolve 'user-service' [A(1)] after 2 queries
         * Caused by: org.springframework.web.reactive.function.client.WebClientRequestException: Failed to resolve 'user-service' [A(1)] after 2 queries
         * 	at org.springframework.web.reactive.function.client.ExchangeFunctions$DefaultExchangeFunction.lambda$wrapException$9(ExchangeFunctions.java:137) ~[spring-webflux-6.2.0.jar:6.2.0]
         * 	Suppressed: reactor.core.publisher.FluxOnAssembly$OnAssemblyException:
         * Error has been observed at the following site(s):
         * 	*__checkpoint ⇢ Request to GET http://user-service/hello/a [DefaultWebClient]
         *.................
         * ..................
         * ..................
         * Caused by: io.netty.resolver.dns.DnsErrorCauseException: Query failed with NXDOMAIN
         * 	at io.netty.resolver.dns.DnsResolveContext.onResponse(..)(Unknown Source) ~[netty-resolver-dns-4.1.115.Final.jar:4.1.115.Final]
         */
        return WebClient.builder().build();
        /********/
    }


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, RedissonClient redisson,
                                           WebClient loadBalancedWebClient) {

        WebClient webClient = loadBalancedWebClient;
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
