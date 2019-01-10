package com.boot.integration.conf.repeat;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * key生成器
 */
public interface LockKeyGenerator
{
    String getLockKey(ProceedingJoinPoint pjp);
}
