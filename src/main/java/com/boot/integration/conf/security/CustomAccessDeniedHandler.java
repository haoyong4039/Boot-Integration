package com.boot.integration.conf.security;

import com.boot.integration.dto.ResponseDto;
import com.boot.integration.exeption.CustomCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token授权失败
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler
{
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws ServletException
    {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setRetCode(CustomCode.ERROR_ACCESS_DENIED.getValue());
        responseDto.setRetData(accessDeniedException.getMessage());

        response.setContentType("application/json");
        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getOutputStream(), responseDto);
        }
        catch (Exception e)
        {
            throw new ServletException();
        }
    }
}
