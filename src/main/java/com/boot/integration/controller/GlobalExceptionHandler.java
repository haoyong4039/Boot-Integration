package com.boot.integration.controller;

import com.boot.integration.dto.ResponseDto;
import com.boot.integration.exeption.CustomCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * TODO: 访问权限不足异常
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseDto AccessDeniedExceptionHandler(AccessDeniedException exception)
    {
        logger.info(exception.getMessage());

        ResponseDto responseDto = new ResponseDto();
        responseDto.setRetCode(CustomCode.ERROR_ACCESS_DENIED.getValue());
        responseDto.setRetData(exception.getMessage());

        return responseDto;
    }
}
