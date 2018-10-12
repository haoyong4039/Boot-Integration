package com.boot.integration.conf.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * 过滤器
 */
@Component
@WebFilter(filterName = "customFilter", urlPatterns = "/*")
public class CustomFilter implements Filter
{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        logger.info("filter init success");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        String url = request.getRequestURI();

        logger.info("visit url:{}",url);

        Principal principal = request.getUserPrincipal();

        logger.info("principal:{}",principal);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy()
    {

    }
}
