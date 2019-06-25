package com.boot.integration.conf.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.boot.integration.exeption.CustomCode;
import com.boot.integration.exeption.CustomException;
import com.boot.integration.util.data.ObjectMapperUtils;
import com.boot.integration.util.web.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

/**
 * <pre>
 * <一句话功能简述>
 * 定义全局日志
 * 进入方法前进行请求处理
 * <功能详细描述>
 * </pre>
 */
@Aspect
@Component
public class HttpRequestAspect
{

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestAspect.class);

    /**
     * @PointCut 注解表示表示横切点，哪些方法需要被横切
     * print() 切点签名
     */
    @Pointcut("execution(public * com.boot.integration.controller.*.*(..))")
    public void print()
    {

    }

    @Around("print()")
    public Object around(ProceedingJoinPoint pjp) throws CustomException
    {
        try
        {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();

            // TODO:此处可进行对请求的判断，成功则pjp.proceed()，失败则throw new CustomException。

            return pjp.proceed();// 返回原方法的返回值
        }
        catch (CustomException e)
        {
            throw e;
        }
        catch (Throwable e)
        {
            throw new CustomException(CustomCode.ERROR_SYSTEM);
        }
    }
}