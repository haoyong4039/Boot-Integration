package com.boot.integration.conf.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig
{

    private CorsConfiguration buildConfig()
    {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 1. 允许所有的请求域名访问，可以固定单条或者多条内容，如："http://www.baidu.com"，只有百度可以访问我们的跨域资源。
        corsConfiguration.addAllowedOrigin("*");
        // 2. 允许所有的请求头访问，可以自定义设置任意请求头信息，如："X-YAUTH-TOKEN"。
        corsConfiguration.addAllowedHeader("*");
        // 3. 允许所有的请求方法访问，如：POST、GET、PUT、DELETE等。
        corsConfiguration.addAllowedMethod("*");

        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter()
    {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 4. 配置可以被跨域的路径，可以任意配置，可以具体到直接请求路径。
        source.registerCorsConfiguration("/**", buildConfig());

        return new CorsFilter(source);
    }
}
