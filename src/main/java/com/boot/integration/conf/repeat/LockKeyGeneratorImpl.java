package com.boot.integration.conf.repeat;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        // 获取连接点的方法签名对象
        MethodSignature signature = (MethodSignature)pjp.getSignature();
        Method method = signature.getMethod();
        // 获取方法上的CacheLock注解
        CacheLock lockAnnotation = method.getAnnotation(CacheLock.class);

        // 获取方法的所有参数
        final Parameter[] parameters = method.getParameters();
        // 获取方法的所有参数值
        final Object[] args = pjp.getArgs();

        StringBuilder builder = new StringBuilder();

        // 解析含CacheParam注解的参数
        for (int i = 0; i < parameters.length; i++)
        {
            // 提取CacheParam注解
            final CacheParam annotation = parameters[i].getAnnotation(CacheParam.class);
            // 如果为空，说明该参数不含CacheParam注解
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
