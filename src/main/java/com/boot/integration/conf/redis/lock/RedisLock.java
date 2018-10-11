package com.boot.integration.conf.redis.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Explain:Redis分布式锁
 */
@Component
public class RedisLock
{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 加锁
     *
     * @param key   商品的唯一标志
     * @param value 当前时间+超时时间 也就是时间戳
     */
    public boolean lock(String key, String value)
    {
        //thread:t_B key:A value:B
        //thread:t_C key:A value:C
        logger.info("lock[key:{},value:{}]", key, value);

        // 判断key是否存在，不存在则更新，否则放弃
        if (stringRedisTemplate.opsForValue().setIfAbsent(key, value))
        {
            return true;
        }

        //获取value值
        //key:A value:A
        String currentValue = stringRedisTemplate.opsForValue().get(key);

        // 如果锁过期
        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis())
        {
            // 获取上一个key，并重新上锁
            // 假设两个线程同时进来这里，因为key被占用了，而且锁过期了。但获取的key都是A，value都是A
            // 而这里面的getAndSet一次只会一个执行，也就是一个执行之后，value已经变成了B或C。
            // 只有一个线程获取的value会是A，另一个线程拿到的value是B或C。
            String oldValue = stringRedisTemplate.opsForValue().getAndSet(key, value);

            // 拿到正确锁的线程返回true
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 解锁
     *
     * @param key
     * @param value
     */
    public void unlock(String key, String value)
    {
        try
        {
            logger.info("unlock[key:{},value:{}]", key, value);

            String currentValue = stringRedisTemplate.opsForValue().get(key);

            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value))
            {
                stringRedisTemplate.opsForValue().getOperations().delete(key);
            }
        }
        catch (Exception e)
        {
            logger.error("[Redis分布式锁] 解锁出现异常了，{}", e);
        }
    }
}
