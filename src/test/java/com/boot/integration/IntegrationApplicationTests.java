package com.boot.integration;

import com.boot.integration.conf.rabbit.MessageProducer;
import com.boot.integration.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationApplicationTests
{

    @Autowired
    private MessageProducer messageProducer;

    @Test
    public void send()
    {
        User user = new User();
        user.setUsername("haoyong");
        messageProducer.sendMessage(user);
    }

}
