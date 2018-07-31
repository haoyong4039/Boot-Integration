//package com.boot.integration.controller;
//
//import com.boot.integration.conf.rabbit.MessageProducer;
//import com.boot.integration.model.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/rabbit")
//public class RabbitController {
//
//    @Autowired
//    private MessageProducer messageProducer;
//
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    @RequestMapping(value = "/test",method = RequestMethod.GET)
//    public void testRabbit(){
//        User user = new User();
//        user.setUsername("author");
//        messageProducer.sendMessage(user);
//    }
//}
