package com.crm.redis_impl.service.redisService;

public interface RedisService {
    public <T> T get(String key, Class<T> tClass);

    public void set(String key, Object value, Long ttl);

    public void delete(String key);
}
