package com.boot.integration.conf.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class RedisConfig
{

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private RedisProperties redisProperties;

    @Bean
    public RedisTemplate redisTemplateInit()
    {
        // 设置序列化Key的实例化对象
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // 设置序列化Value的实例化对象
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }

    @Bean
    public JedisCluster getJedisCluster()
    {
        //获取服务器数组
        String[] serverArray = redisProperties.getClusterNodes().split(",");

        Set<HostAndPort> nodes = new HashSet<>();

        for (String ipPort : serverArray)
        {
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
        }

        return new JedisCluster(nodes, new GenericObjectPoolConfig());// 可设置密码
    }
}
