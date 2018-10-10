package com.boot.integration.listener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * <pre>
 * <一句话功能简述>
 * 监听服务配置类
 * <功能详细描述>
 * </pre>
 * 
 * @author haoyong
 */
@Configuration
public class ListenerConfig
{

    @Bean
    public ApplicationStartListener applicationStartListener()
    {
        return new ApplicationStartListener();
    }

}
