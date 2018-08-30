package com.boot.integration.conf.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
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

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DruidProperties druidProperties;

    @Bean
    public DataSource dataSource()
    {
        DruidDataSource dataSource = new DruidDataSource();

        dataSource.setUrl(druidProperties.getUrl());
        dataSource.setUsername(druidProperties.getUsername());
        dataSource.setPassword(druidProperties.getPassword());
        dataSource.setDriverClassName(druidProperties.getDriverClass());

        dataSource.setInitialSize(druidProperties.getInitialSize());
        dataSource.setMinIdle(druidProperties.getMinIdle());
        dataSource.setMaxActive(druidProperties.getMaxActive());

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

    @Bean
    public ServletRegistrationBean druidServlet()
    {
        logger.info("init Druid Servlet Configuration ");

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        // IP白名单
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        // IP黑名单(共同存在时，deny优先于allow)
        servletRegistrationBean.addInitParameter("deny", "192.168.7.190");
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", "haoyong");
        servletRegistrationBean.addInitParameter("loginPassword", "haoyong");
        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", "false");

        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean()
    {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");

        return filterRegistrationBean;
    }
}
