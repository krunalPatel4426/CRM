package com.crm.redis_impl.config.exception.customException;

import org.springframework.cache.interceptor.CacheErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;

public class CustomCacheErrorHandler implements CacheErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(CustomCacheErrorHandler.class);

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        log.error("Redis GET Error: Key {} in cache {} - Reason: {}", key, cache.getName(), exception.getMessage());
        // By doing nothing here, Spring continues the execution and hits the Database (Fail-safe)
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        log.error("Redis PUT Error: Key {} in cache {} - Reason: {}", key, cache.getName(), exception.getMessage());
    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        log.error("Redis EVICT Error: Key {} in cache {} - Reason: {}", key, cache.getName(), exception.getMessage());
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        log.error("Redis CLEAR Error: Cache {} - Reason: {}", cache.getName(), exception.getMessage());
    }
}