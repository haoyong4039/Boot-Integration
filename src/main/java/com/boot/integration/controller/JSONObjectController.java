package com.boot.integration.controller;

import com.alibaba.fastjson.JSONObject;
import com.boot.integration.model.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/jsonObject")
public class JSONObjectController {

    private static final Logger log = LoggerFactory.getLogger(JSONObjectController.class);

    public static final ObjectMapper objectMapper = new ObjectMapper();

    /*
    * 使用的是com.alibaba.fastjson.JSONObject
    * */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/read",method = RequestMethod.POST)
    public void readValue(@RequestBody JSONObject jsonObject){
        //fastjson提供了属性直接取值
        log.info("[username] - [{}]......[password] - [{}]",jsonObject.getString("username"),jsonObject.getString("password"));
        try {
            //objectMapper提供了readValue方法，将string字符串根据Class对象属性名封装
            User user = objectMapper.readValue(jsonObject.toString(),User.class);
            log.info("[user] - [{}]",user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
