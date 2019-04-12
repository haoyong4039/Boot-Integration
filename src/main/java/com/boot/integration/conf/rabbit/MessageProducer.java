package com.boot.integration.conf.rabbit;

import com.boot.integration.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MessageProducer
{

    private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback()
    {
        @Override
        public void confirm(CorrelationData correlationData, boolean b, String s)
        {
            System.out.println(b);
        }
    };

    public void sendMessage(User user)
    {
        // 确认回调
        this.rabbitTemplate.setConfirmCallback(confirmCallback);
        // 添加延时队列
        this.rabbitTemplate.convertAndSend(RabbitConfig.DELAY_EXCHANGE, RabbitConfig.DELAY_ROUTING_KEY, user,
            message -> {
                // TODO 第一句是可要可不要,根据自己需要自行处理
                message.getMessageProperties()
                    .setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, User.class.getName());
                // TODO 如果配置了 params.put("x-message-ttl", 5 * 1000); 那么这一句也可以省略,具体根据业务需要是声明 Queue 的时候就指定好延迟时间还是在发送自己控制时间
                message.getMessageProperties().setExpiration(6 * 1000 + "");
                return message;
            });
        log.info("[发送时间] - [{}]", new Date());
    }
}
