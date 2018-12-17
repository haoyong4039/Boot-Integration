package com.boot.integration.exeption;

import com.boot.integration.util.web.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler
{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(CustomException.class)
    public Object customExceptionHandler(CustomException e)
    {
        logger.info("CustomExceptionHandler:{}",e);

        return BaseResponse.getResponseMap(e.getValue(),e.getMessage(),null);
    }
}
