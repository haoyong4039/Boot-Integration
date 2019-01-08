package com.boot.integration.conf.aop;

import javax.servlet.http.HttpServletRequest;

import com.boot.integration.exeption.CustomCode;
import com.boot.integration.exeption.CustomException;
import com.boot.integration.util.data.ObjectMapperUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


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

    public static long startTime;

    public static long endTime;

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

            String accessToken = request.getHeader("AccessToken");

            // TODO:此处可进行对请求的判断

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

    @Before("print()")
    public void before(JoinPoint joinPoint) throws CustomException
    {
        startTime = System.currentTimeMillis();

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        String url = request.getRequestURI();
        String ip = request.getRemoteAddr();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String methodType = request.getMethod();
        Object[] args = joinPoint.getArgs();

        logger.info("=================================");

        logger.info("url - {}", url);
        logger.info("ip - {}", ip);
        logger.info("className - {}", className);
        logger.info("methodName - {}", methodName);
        logger.info("methodType - {}", methodType);
        logger.info("args - {}", ObjectMapperUtils.convertObjectToString(args));

        logger.info("=================================");
    }

    @After("print()")
    public void after()
    {
        endTime = System.currentTimeMillis();

        logger.info("time - {}ms", endTime - startTime);
    }
}