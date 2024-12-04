package com.acgsocial.user.gateway.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cache.redis")
public class CacheRedisProperties {

    /**
     * Database index used by the connection factory.
     */
    private int database = 0;

    /**
     * Redis server host.
     */
    private String host = "localhost";

    /**
     * Login password of the Redis server.
     */
    private String password;

    /**
     * Redis server port.
     */
    private int port = 6379;

    /**
     * Connection timeout in milliseconds.
     */
    private int timeout = 0;

    /**
     * Pool properties for connection pooling.
     */
    private RedisProperties.Pool pool = new RedisProperties.Pool();

    /**
     * Sentinel properties for Redis Sentinel configuration.
     */
}