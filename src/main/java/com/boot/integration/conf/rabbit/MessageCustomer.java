//package com.boot.integration.conf.rabbit;
//
//import com.boot.integration.model.User;
//import com.rabbitmq.client.Channel;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.Date;
//
//@Component
//public class MessageCustomer {
//
//    private static final Logger log = LoggerFactory.getLogger(MessageCustomer.class);
//
//    @RabbitListener(queues = {RabbitConfig.COMMON_QUEUE})
//    public void listenerDelayQueue(User user, Message message, Channel channel) {
//        log.info("[消费时间] - [{}] - [{}]", new Date(), user.toString());
//        try {
//            // TODO 通知 MQ 消息已被成功消费,可以ACK了
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        } catch (IOException e) {
//            // TODO 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
//        }
//    }
//}
