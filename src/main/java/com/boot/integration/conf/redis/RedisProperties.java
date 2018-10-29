package com.boot.integration.conf.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties
{
    private String clusterNodes;

    public String getClusterNodes()
    {
        return clusterNodes;
    }

    public void setClusterNodes(String clusterNodes)
    {
        this.clusterNodes = clusterNodes;
    }
}
