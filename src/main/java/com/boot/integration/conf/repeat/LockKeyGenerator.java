package com.boot.integration.conf.repeat;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * key生成器
 */
public interface LockKeyGenerator
{

    /**
     * 获取AOP参数,生成指定缓存Key
     *
     * @param pjp PJP
     * @return 缓存KEY
     */
    String getLockKey(ProceedingJoinPoint pjp);
}
