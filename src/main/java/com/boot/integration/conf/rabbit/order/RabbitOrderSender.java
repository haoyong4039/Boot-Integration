package com.boot.integration.conf.rabbit.order;

import com.boot.integration.constant.Constants;
import com.boot.integration.mapper.BrokerMessageLogMapper;
import com.boot.integration.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 订单发送
 */
@Component
public class RabbitOrderSender
{
    private static Logger logger = LoggerFactory.getLogger(RabbitOrderSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    // 回调函数: confirm确认
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback()
    {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause)
        {
            logger.info("correlationData:{},ack:{},cause:{}", correlationData, ack, cause);

            String messageId = correlationData.getId();

            if (ack)
            {
                // 如果confirm返回成功 则进行更新
                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageId, Constants.ORDER_SEND_SUCCESS, new Date());
            }
            else
            {
                // 失败则进行具体的后续操作:重试 或者补偿等手段
                System.err.println("异常处理...");
            }
        }
    };

    // 发送消息方法调用: 构建自定义对象消息
    public void sendOrder(Order order) throws Exception
    {
        // 通过实现 ConfirmCallback 接口，消息发送到 Broker 后触发回调，确认消息是否到达 Broker 服务器，也就是只确认是否正确到达 Exchange 中
        rabbitTemplate.setConfirmCallback(confirmCallback);
        // 消息唯一ID
        CorrelationData correlationData = new CorrelationData(order.getMessageId());
        rabbitTemplate.convertAndSend("order-exchange", "order.ABC", order, correlationData);
    }
}
