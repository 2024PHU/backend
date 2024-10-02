package com.phu.backend.service.redis;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisInitializationService {
    private final RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void clearRedisCache() {
        try {
            log.info("Initializing Redis: Clearing all data");
            redisTemplate.getConnectionFactory().getConnection().flushAll();
            log.info("Redis data cleared successfully");
        } catch (Exception e) {
            log.error("Error occurred while clearing Redis data", e);
        }
    }
}