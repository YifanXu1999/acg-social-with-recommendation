package com.acgsocial.user.gateway.util.redis;

import com.acgsocial.user.gateway.domain.dto.KeyValue;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JacksonCodec;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;

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

    public void setHashMap(String key, List<KeyValue> keyValueList, Duration duration) {
        RMap<String, Object> map = redissonClient.getMap(key);
        keyValueList.stream().forEach(keyValue -> map.put(keyValue.getKey(), keyValue.getValue()));
        // TODO Modify duration of hashmaps
        map.expire(duration);
    }

    public RMap<String, Object> getHashMap(String key) {
        RMap<String, Object> map = redissonClient.getMap(key);
        return map;
    }



}
