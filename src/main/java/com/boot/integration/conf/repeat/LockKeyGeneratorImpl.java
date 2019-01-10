package com.boot.integration.conf.repeat;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
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

    @Override
    public String getLockKey(ProceedingJoinPoint pjp)
    {
        MethodSignature signature = (MethodSignature)pjp.getSignature();
        Method method = signature.getMethod();
        // 获取方法上的CacheLock注解
        CacheLock lockAnnotation = method.getAnnotation(CacheLock.class);

        // 1.获取到所有的参数值的数组
        final Object[] args = pjp.getArgs();
        // 2.获取到方法的所有参数名称的字符串数组
        final Parameter[] parameters = method.getParameters();

        StringBuilder builder = new StringBuilder();

        // TODO 默认解析方法里面带 CacheParam 注解的属性,如果没有尝试着解析实体对象中的
        for (int i = 0; i < parameters.length; i++)
        {
            // 3.获取参数中的CacheParam注解
            final CacheParam annotation = parameters[i].getAnnotation(CacheParam.class);
            if (annotation == null)
            {
                continue;
            }
            builder.append(lockAnnotation.delimiter()).append(args[i]);
        }
        if (StringUtils.isEmpty(builder.toString()))
        {
            final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; i++)
            {
                final Object object = args[i];
                final Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields)
                {
                    final CacheParam annotation = field.getAnnotation(CacheParam.class);
                    if (annotation == null)
                    {
                        continue;
                    }
                    field.setAccessible(true);
                    builder.append(lockAnnotation.delimiter()).append(ReflectionUtils.getField(field, object));
                }
            }
        }
        return lockAnnotation.prefix() + builder.toString();
    }
}
