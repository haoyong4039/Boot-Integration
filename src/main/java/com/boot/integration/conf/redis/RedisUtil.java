package com.boot.integration.conf.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil
{
    @Autowired
    private RedisTemplate redisTemplate;

    /*============================================判断是否存在key=============================================*/
    public boolean hasKey(String key)
    {
        boolean iKey = redisTemplate.hasKey(key);
        if (iKey)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*============================================设置缓存时间=============================================*/
    // seconds为0时不设置，默认永久
    public void expire(String key, int seconds)
    {
        if (seconds > 0)
        {
            redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        }
    }

    /*==============================================key/value存储===============================================*/
    public void set(String key, Object value, int seconds)
    {
        redisTemplate.opsForValue().set(key, value);
        expire(key, seconds);
    }

    public Object get(String key)
    {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key)
    {
        redisTemplate.delete(key);
    }

    /*==============================================key/field/value存储===============================================*/
    public void hashSet(String key, String field, Object value, int seconds)
    {
        redisTemplate.opsForHash().put(key, field, value);
        expire(key, seconds);
    }

    public Object hashGet(String key, String field)
    {
        return redisTemplate.opsForHash().get(key, field);
    }

    public void hashDelete(String key, String field)
    {
        redisTemplate.opsForHash().delete(key, field);
    }

    public Map<String, Object> hashGetAll(String key)
    {
        return redisTemplate.opsForHash().entries(key);
    }
}
