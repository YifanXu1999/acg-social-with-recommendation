package com.acgsocial.user.gateway.util.redis;

import com.acgsocial.user.gateway.domain.dao.UserGatewayDetail;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JacksonCodec;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisUtil {

    private final RedissonClient redissonClient;

    public  <T> T getJson(String key, Class<T> clazz) {
        JacksonCodec<T> codec = new JacksonCodec<>(clazz);
        redissonClient.getJsonBucket(key, codec);
        return (T) redissonClient.getBucket(key).get();
    }

    public <T> void setJson(String key, T value) {
        JacksonCodec<T> codec = (JacksonCodec<T>) new JacksonCodec<>(value.getClass());
        redissonClient.getJsonBucket(key, codec).set(value);
    }



}
