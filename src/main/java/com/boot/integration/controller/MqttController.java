package com.boot.integration.controller;

import com.boot.integration.conf.mtqq.client.ClientOneService;
import com.boot.integration.conf.mtqq.client.ClientTwoService;
import com.boot.integration.util.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mqtt")
public class MqttController
{

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/one", method = RequestMethod.GET)
    public void clientOne()
    {
        ClientOneService clientOneService = BeanUtils.getObjectBean("clientOneService");
        clientOneService.send("two", "你好啊，two");
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/two", method = RequestMethod.GET)
    public void clientTwo()
    {
        ClientTwoService clientTwoService = BeanUtils.getObjectBean("clientTwoService");
        clientTwoService.send("one", "你好啊，one");
    }
}
