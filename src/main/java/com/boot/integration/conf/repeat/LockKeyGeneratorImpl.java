package com.boot.integration.conf.repeat;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 通过注解参数生成锁;
 *
 * @author haoyong
 */
@Service
public class LockKeyGeneratorImpl implements LockKeyGenerator
{

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String getLockKey(ProceedingJoinPoint pjp)
    {
        MethodSignature signature = (MethodSignature)pjp.getSignature();
        Method method = signature.getMethod();
        // 获取方法上的CacheLock注解
        CacheLock lockAnnotation = method.getAnnotation(CacheLock.class);

        // 1.获取方法的所有参数
        final Parameter[] parameters = method.getParameters();
        // 2.获取方法的所有参数值
        final Object[] args = pjp.getArgs();

        StringBuilder builder = new StringBuilder();

        // 解析方法里面带CacheParam注解的参数
        for (int i = 0; i < parameters.length; i++)
        {
            // 3.获取带CacheParam注解的参数
            final CacheParam annotation = parameters[i].getAnnotation(CacheParam.class);
            if (annotation == null)
            {
                continue;
            }
            builder.append(lockAnnotation.delimiter()).append(args[i]);
        }

        if (StringUtils.isEmpty(builder.toString()))
        {
            logger.info("@CacheParam not exist");
        }

        return lockAnnotation.prefix() + builder.toString();
    }
}
