package com.boot.integration.conf.repeat;

import java.lang.annotation.*;

/**
 * 锁的参数
 *
 * @author Levin
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheParam
{

    /**
     * 字段名称
     */
    String name() default "";
}
