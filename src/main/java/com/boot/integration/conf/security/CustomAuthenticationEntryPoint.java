package com.boot.integration.conf.security;

import com.boot.integration.exeption.CustomCode;
import com.boot.integration.util.web.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 检验token异常
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint
{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws ServletException
    {
        response.setContentType("application/json");

        String retCode = CustomCode.ERROR_TOKEN.getValue();
        String retMsg = authException.getMessage();
        Map<String, Object> responseMap = BaseResponse.getResponseMap(retCode, retMsg, "");

        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getOutputStream(), responseMap);
        }
        catch (Exception e)
        {
            throw new ServletException();
        }
    }
}