package com.boot.integration.controller;

import com.boot.integration.conf.redis.RedisUtil;
import com.boot.integration.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/redis")
public class RedisController {

    private static final Logger log = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private RedisUtil redisUtil;

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
}
