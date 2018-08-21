//package com.boot.integration.conf.rabbit;
//
//import org.springframework.amqp.core.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * RabbitMQ配置
// *
// * @author Levin
// * @since 2018/4/11 0011
// */
//@Configuration
//public class RabbitConfig
//{
//
//    public static final String DELAY_QUEUE = "delay.queue";
//
//    public static final String DELAY_EXCHANGE = "delay.exchange";
//
//    public static final String DELAY_ROUTING_KEY = "delay";
//
//    public static final String COMMON_QUEUE = "common.queue";
//
//    public static final String COMMON_EXCHANGE = "common.exchange";
//
//    public static final String COMMON_ROUTING_KEY = "common";
//
//    /*=====================================Direct交换机=======================================*/
//    @Bean
//    public Queue delayQueue()
//    {
//        Map<String, Object> params = new HashMap<>();
//        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
//        params.put("x-dead-letter-exchange", COMMON_EXCHANGE);
//        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
//        params.put("x-dead-letter-routing-key", COMMON_ROUTING_KEY);
//        return new Queue(DELAY_QUEUE, true, false, false, params);
//    }
//
//    @Bean
//    public DirectExchange delayExchange()
//    {
//        return new DirectExchange(DELAY_EXCHANGE);
//    }
//
//    @Bean
//    public Binding dlxBinding()
//    {
//        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(DELAY_ROUTING_KEY);
//    }
//
//    /*=====================================Topic交换机=======================================*/
//    @Bean
//    public Queue commonQueue()
//    {
//        return new Queue(COMMON_QUEUE, true);
//    }
//
//    @Bean
//    public TopicExchange commonExchange()
//    {
//        return new TopicExchange(COMMON_EXCHANGE);
//    }
//
//    @Bean
//    public Binding commonBinding()
//    {
//        return BindingBuilder.bind(commonQueue()).to(commonExchange()).with(COMMON_ROUTING_KEY);
//    }
//
//}
