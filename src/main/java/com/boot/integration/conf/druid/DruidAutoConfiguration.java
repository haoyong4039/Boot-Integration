package com.boot.integration.conf.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by haoyong on 2018/1/4.
 */
@Configuration
@EnableConfigurationProperties(DruidProperties.class)
public class DruidAutoConfiguration
{

    @Autowired
    private DruidProperties druidProperties;

    @Bean
    public DataSource dataSource()
    {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(druidProperties.getUrl());
        dataSource.setUsername(druidProperties.getUsername());
        dataSource.setPassword(druidProperties.getPassword());
        if (druidProperties.getInitialSize() > 0)
        {
            dataSource.setInitialSize(druidProperties.getInitialSize());
        }
        if (druidProperties.getMinIdle() > 0)
        {
            dataSource.setMinIdle(druidProperties.getMinIdle());
        }
        if (druidProperties.getMaxActive() > 0)
        {
            dataSource.setMaxActive(druidProperties.getMaxActive());
        }
        dataSource.setTestOnBorrow(druidProperties.isTestOnBorrow());
        try
        {
            dataSource.init();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return dataSource;
    }
}
