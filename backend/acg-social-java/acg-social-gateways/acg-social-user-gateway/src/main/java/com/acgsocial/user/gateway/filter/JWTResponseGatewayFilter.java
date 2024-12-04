package com.acgsocial.user.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.redisson.api.RedissonClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class JWTResponseGatewayFilter implements GatewayFilter, Ordered {


    private final RedissonClient redisson;

    public JWTResponseGatewayFilter(RedissonClient redisson) {
        this.redisson = redisson;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Intercept the request
        HttpHeaders headers = exchange.getRequest().getHeaders();

        // Get the session ID
        AtomicReference<String> sessionId = new AtomicReference<>("");
        AtomicReference<String> userId = new AtomicReference<>("");
        exchange.getSession().subscribe(session -> {
            if(! session.isStarted()) {
                session.start();
            }
            sessionId.set(session.getId());
            // TODO Consider not to allow user to procceed if session already registered with user.
        });




        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // Intercept the response
            getDecoratedResponse(exchange.getResponse(), exchange.getRequest(), exchange.getResponse().bufferFactory());
            ServerHttpResponse response = exchange.getResponse();
            response.addCookie(ResponseCookie.from("cookieName", "cookieValue").build());
            String newResponseBody =
              "<body>\n" +
                "      <h1 style=\"color:red;text-align:center\">Bad Request </h1>\n" +
                "<p>" + "</p>" +
                "      <p>If you are seeing this page it means response body is modified.  " + sessionId.get() +"</p>\n" +
                "  </body>";

            DataBuffer dataBuffer = response.bufferFactory().wrap(newResponseBody.getBytes(StandardCharsets.UTF_8));
            response.getHeaders().setContentLength(newResponseBody.length());
            response.writeWith(Mono.just(dataBuffer)).subscribe();
            exchange.mutate().response(response).build();
            return;



        }));
    }

    @Override
    public int getOrder() {
        // Set the order of this filter (lower numbers have higher precedence)
        return 0;
    }

    private void modifyResponse(ServerWebExchange exchange, AtomicReference<String> sessionId) {
        // Intercept the response
        ServerHttpResponse response = exchange.getResponse();

        response.addCookie(ResponseCookie.from("cookieName", "cookieValue").build());
        String newResponseBody =
          "<body>\n" +
            "      <h1 style=\"color:red;text-align:center\">Bad Request </h1>\n" +
            "<p>" + "</p>" +
            "      <p>If you are seeing this page it means response body is modified.  " + sessionId.get() +"</p>\n" +
            "  </body>";

        DataBuffer dataBuffer = response.bufferFactory().wrap(newResponseBody.getBytes(StandardCharsets.UTF_8));
        response.getHeaders().setContentLength(newResponseBody.length());
        response.writeWith(Mono.just(dataBuffer)).subscribe();
        exchange.mutate().response(response).build();
    }

    private ServerHttpResponseDecorator getDecoratedResponse(ServerHttpResponse response, ServerHttpRequest request, DataBufferFactory dataBufferFactory) {
        return new ServerHttpResponseDecorator(response) {


            @Override
            public Mono<Void> writeWith(final Publisher<? extends DataBuffer> body) {
                log.info("requestId: {}, method: {}, url: {}", request.getId(), request.getMethod(), request.getURI());
                if (body instanceof Flux) {

                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;

                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {

                            DefaultDataBuffer joinedBuffers = new DefaultDataBufferFactory().join(dataBuffers);
                            byte[] content = new byte[joinedBuffers.readableByteCount()];
                            joinedBuffers.read(content);
                            String responseBody = new String(content, StandardCharsets.UTF_8);//MODIFY RESPONSE and Return the Modified response
                            log.info("requestId: {}, method: {}, url: {}, \nresponse body :{}", request.getId(), request.getMethod(), request.getURI(), responseBody);

                            return dataBufferFactory.wrap(responseBody.getBytes());
                        })
                        .switchIfEmpty(Flux.defer(() -> {

                            System.out.println("Write to database here");
                            return Flux.just();
                        }))
                    ).onErrorResume(err -> {
                        log.error("error while decorating Response: {}", err.getMessage());
                        return Mono.empty();
                    });

                } else {
                    System.out.println("2000000000");
                }
                return super.writeWith(body);
            }
        };
    }
}
