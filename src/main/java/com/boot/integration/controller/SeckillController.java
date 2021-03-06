package com.boot.integration.controller;

import com.boot.integration.conf.seckill.SeckillService;
import com.boot.integration.exeption.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seckill")
public class SeckillController
{

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public void start()
    {
        Thread t_A = new Thread()
        {
            public void run()
            {
                kill("t_A");
            }
        };

        Thread t_B = new Thread()
        {
            public void run()
            {
                kill("t_B");
            }
        };

        Thread t_C = new Thread()
        {
            public void run()
            {
                kill("t_C");
            }
        };

        t_A.start();
        t_B.start();
        t_C.start();
    }

    public void kill(String name)
    {
        try
        {
            System.out.println("==================================================");

            String detail = seckillService.queryDetail("10010");

            System.out.println(detail);

            seckillService.secKill("10010");

            System.out.println(name + "-->已秒杀");

            detail = seckillService.queryDetail("10010");

            System.out.println(detail);
        }
        catch (CustomException e)
        {
            e.printStackTrace();
        }
    }
}
