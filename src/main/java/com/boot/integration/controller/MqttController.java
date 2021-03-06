package com.boot.integration.controller;

import com.boot.integration.conf.mtqq.service.ClientOneService;
import com.boot.integration.conf.mtqq.service.ClientTwoService;
import com.boot.integration.util.plat.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mqtt")
public class MqttController
{

    @RequestMapping(value = "/one", method = RequestMethod.GET)
    public void clientOne()
    {
        ClientOneService clientOneService = BeanUtils.getObjectBean("clientOneService");
        clientOneService.send("two", "你好啊，two");
    }

    @RequestMapping(value = "/two", method = RequestMethod.GET)
    public void clientTwo()
    {
        ClientTwoService clientTwoService = BeanUtils.getObjectBean("clientTwoService");
        clientTwoService.send("one", "你好啊，one");
    }
}
