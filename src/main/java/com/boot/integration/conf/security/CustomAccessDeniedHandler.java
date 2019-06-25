package com.boot.integration.conf.security;

import com.boot.integration.exeption.CustomCode;
import com.boot.integration.util.web.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * token授权失败
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler
{
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws ServletException
    {
        response.setContentType("application/json");

        String retCode = CustomCode.ERROR_ACCESS_DENIED.getValue();
        String retMsg = accessDeniedException.getMessage();
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
