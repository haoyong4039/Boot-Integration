package com.boot.integration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableWebMvc
@EnableWebSocket
@MapperScan(basePackages = "com.boot.integration.mapper")
@SpringBootApplication
public class IntegrationApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(IntegrationApplication.class, args);
    }
}
