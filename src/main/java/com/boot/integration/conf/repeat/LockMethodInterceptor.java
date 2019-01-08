package com.boot.integration.conf.repeat;

import com.boot.integration.exeption.CustomCode;
import com.boot.integration.exeption.CustomException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * redis 方案
 */
@Aspect
@Configuration
public class LockMethodInterceptor
{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private LockKeyGenerator lockKeyGenerator;

    @Around("execution(public * *(..)) && @annotation(com.boot.integration.conf.repeat.CacheLock)")
    public Object interceptor(ProceedingJoinPoint pjp) throws CustomException
    {
        MethodSignature signature = (MethodSignature)pjp.getSignature();
        Method method = signature.getMethod();
        CacheLock lock = method.getAnnotation(CacheLock.class);
        if (StringUtils.isEmpty(lock.prefix()))
        {
            throw new CustomException(CustomCode.ERROR_LOCK_KEY_NULL);
        }
        final String lockKey = lockKeyGenerator.getLockKey(pjp);

        logger.info("lockKey - {}",lockKey);

        try
        {
            final Boolean success = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "1");
            if (!success)
            {
                throw new CustomException(CustomCode.ERROR_REPEAT);
            }
            stringRedisTemplate.expire(lockKey, lock.expire(), lock.timeUnit());
            try
            {
                return pjp.proceed();
            }
            catch (Throwable throwable)
            {
                throw new CustomException(CustomCode.ERROR_SYSTEM);
            }
        }
        finally
        {
            // TODO 如果演示的话需要注释该代码;实际应该放开
            // stringRedisTemplate.delete(lockKey);
        }
    }
}
