package com.boot.integration.controller;

import com.boot.integration.conf.mtqq.client.ClientOneService;
import com.boot.integration.conf.mtqq.client.ClientTwoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mqtt")
public class MqttController
{

    @Resource
    private ClientOneService clientOneService;

    @Resource
    private ClientTwoService clientTwoService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/one", method = RequestMethod.GET)
    public void clientOne()
    {
        clientOneService.send("two", "你好啊，two");
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/two", method = RequestMethod.GET)
    public void clientTwo()
    {
        clientTwoService.send("one", "你好啊，one");
    }
}
