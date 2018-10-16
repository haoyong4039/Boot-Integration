package com.boot.integration.listener;

import com.boot.integration.conf.mtqq.service.ClientOneService;
import com.boot.integration.conf.mtqq.service.ClientTwoService;
import com.boot.integration.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;

/**
 * <pre>
 * <一句话功能简述>
 * 服务监听类，当启动服务以后初始化业务
 * <功能详细描述>
 * </pre>
 *
 * @author haoyong
 */
public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent>
{
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ClientOneService clientOneService;

    @Resource
    private ClientTwoService clientTwoService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent)
    {

        ApplicationContext context = contextRefreshedEvent.getApplicationContext();

        BeanUtils.setFactory(context);

//        initMqttClient();

        logger.info("application start init success");
    }

    public void initMqttClient()
    {
        clientOneService.start();

        clientTwoService.start();
    }
}
