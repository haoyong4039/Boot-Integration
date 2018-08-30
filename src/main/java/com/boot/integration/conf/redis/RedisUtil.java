package com.boot.integration.conf.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil
{
    @Autowired
    private RedisTemplate redisTemplate;

    //判断是否存在key
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

    /*==============================================key value 存储===============================================*/
    //设置key/value值（无过期）
    public void setValue(String key, Object value)
    {
        redisTemplate.opsForValue().set(key, value);
    }

    //设置key/value值（有过期）
    public void setValue(String key, Object value, int time, TimeUnit unit)
    {
        redisTemplate.opsForValue().set(key, value, time, unit);
    }

    //获取key/value值
    public Object getValue(String key)
    {
        return redisTemplate.opsForValue().get(key);
    }

    //同时设置多个key/value
    public void multiSet(Map<String, Object> map)
    {
        redisTemplate.opsForValue().multiSet(map);
    }

    //同时获取多个key/value
    public List<Object> multiGet(Collection<String> keys)
    {
        return redisTemplate.opsForValue().multiGet(keys);
    }

}
