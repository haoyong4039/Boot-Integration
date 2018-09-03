package com.boot.integration.conf.security;

import com.boot.integration.dto.ResponseDto;
import com.boot.integration.exeption.CustomCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 检验token异常
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint
{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
        throws ServletException
    {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setRetCode(CustomCode.ERROR_TOKEN.getValue());
        responseDto.setRetData(authException.getMessage());

        response.setContentType("application/json");
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), responseDto);
        }
        catch (Exception e)
        {
            throw new ServletException();
        }
    }
}