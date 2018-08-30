package com.boot.integration.conf.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义401错误码内容
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint
{

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ae)
        throws IOException
    {

        logger.info("pre-authenticated entry point called. Rejecting access");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
    }
}
