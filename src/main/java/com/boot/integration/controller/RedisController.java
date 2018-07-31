package com.boot.integration.controller;

import com.alibaba.fastjson.JSONObject;
import com.boot.integration.conf.redis.RedisUtil;
import com.boot.integration.model.User;
import com.boot.integration.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/redis")
public class RedisController {

    private static final Logger log = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/set/multi",method = RequestMethod.GET)
    public void setMultiValue(){
        Map<String,Object> map = new HashMap<>();
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        map.put("keyOne","这只是一个字符串");
        map.put("keyTwo",user);
        redisUtil.multiSet(map);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/get/multi",method = RequestMethod.GET)
    public void getMultiValue(){
        List<String> keys = new ArrayList<>();
        keys.add("keyOne");
        keys.add("keyTwo");
        List<Object> values = redisUtil.multiGet(keys);
        log.info("[user] - [{}]",values);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/super/execute",method = RequestMethod.GET)
    public String supExecute(){
        ExecutorService service = Executors.newCachedThreadPool(); //创建一个线程池
        final CountDownLatch cdOrder = new CountDownLatch(1);//指挥官的命令，设置为1，指挥官一下达命令，则cutDown,变为0，战士们执行任务
        final CountDownLatch cdAnswer = new CountDownLatch(3);//因为有三个战士，所以初始值为3，每一个战士执行任务完毕则cutDown一次，当三个都执行完毕，变为0，则指挥官停止等待。
        for(int i=0;i<3;i++){
            Runnable runnable = new Runnable(){
                public void run(){
                    try {
                        System.out.println("①");
                        cdOrder.await(); //战士们都处于等待命令状态

                        System.out.println("④");

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        cdAnswer.countDown(); //任务执行完毕，返回给指挥官，cdAnswer减1。
                    }
                }
            };
            service.execute(runnable);//为线程池添加任务
        }
        try {
            Thread.sleep((long)(10000));

            System.out.println("②");
            cdOrder.countDown(); //发送命令，cdOrder减1，处于等待的战士们停止等待转去执行任务。

            System.out.println("③");
            cdAnswer.await(); //命令发送后指挥官处于等待状态，一旦cdAnswer为0时停止等待继续往下执行

            System.out.println("⑤");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        service.shutdown(); //任务结束，停止线程池的所有线程
        return "";
    }
}
