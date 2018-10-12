package com.boot.integration.exeption;

import com.boot.integration.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler
{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(CustomException.class)
    public ResponseDto customExceptionHandler(CustomException e)
    {
        logger.info("CustomException:{}",e);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setRetCode(e.getValue());
        responseDto.setRetData(e.getMessage());

        return responseDto;
    }
}
