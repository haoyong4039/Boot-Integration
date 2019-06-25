//package com.boot.integration;
//
//import com.boot.integration.conf.rabbit.delay.MessageProducer;
//import com.boot.integration.model.Order;
//import com.boot.integration.model.User;
//import com.boot.integration.service.impl.OrderService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.UUID;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class IntegrationApplicationTests
//{
//
//    @Autowired
//    private MessageProducer messageProducer;
//
//    @Autowired
//    private OrderService orderService;
//
//    @Test
//    public void testDelaySend()
//    {
//        User user = new User();
//        user.setUsername("haoyong");
//        messageProducer.sendMessage(user);
//    }
//
//    @Test
//    public void testOrderSend() throws Exception
//    {
//        Order order = new Order();
//        order.setId(20007);
//        order.setName("testOrder");
//        order.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
//        orderService.createOrder(order);
//    }
//}
