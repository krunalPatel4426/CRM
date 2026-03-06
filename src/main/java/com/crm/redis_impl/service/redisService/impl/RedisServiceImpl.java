package com.crm.redis_impl.service.redisService.impl;

import com.crm.redis_impl.service.redisService.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper mapper;


    @Override
    public <T> T get(String key, Class<T> tClass) {
        try{
            Object o = redisTemplate.opsForValue().get(key);
            if (o == null) {
                return null;
            }
            return mapper.readValue(o.toString(), tClass);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void set(String key, Object value, Long ttl) {
        try{
            String json = mapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, json, ttl, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Override
    public void delete(String key) {
        try{
            redisTemplate.delete(key);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

}
