package com.boot.integration.controller;

import com.alibaba.fastjson.JSONObject;
import com.boot.integration.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/object")
public class ObjectController
{

    private static final Logger log = LoggerFactory.getLogger(ObjectController.class);

    public static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     *
     * <pre>
     * <一句话功能简述>
     * 通过JSONObject直接获取属性值
     * <功能详细描述>
     * </pre>
     *
     * @author haoyong
     * @version [版本号, 2018年8月16日]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public void readValue(@RequestBody JSONObject jsonObject){

        log.info("[username] - [{}]......[password] - [{}]",jsonObject.getString("username"),jsonObject.getString("password"));

        try {

            User user = objectMapper.readValue(String.valueOf(jsonObject),User.class);
            log.info("[user] - [{}]",user);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     *
     * <pre>
     * <一句话功能简述>
     * object对象转byte，byte转object
     * <功能详细描述>
     * </pre>
     *
     * @author haoyong
     * @version [版本号, 2018年8月16日]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/convert",method = RequestMethod.GET)
    public void convert(){

        try
        {
            User user = new User();
            user.setUsername("root");
            user.setPassword("admin");

            byte[] objectToBytes = objectMapper.writeValueAsBytes(user);
            log.info("objectToBytes: {}",objectToBytes);

            User bytesToObject = objectMapper.readValue(objectToBytes,User.class);
            log.info("bytesToObject: {}",bytesToObject);


            List<User> userList = new ArrayList<>();
            userList.add(user);

            byte[] objectToBytes2 = objectMapper.writeValueAsBytes(userList);
            log.info("objectToBytes2: {}",objectToBytes2);

            List<User> bytesToObjectList = objectMapper.readValue(objectToBytes2,List(User.class));
            log.info("bytesToObjectList: {}",bytesToObjectList);

        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     *
     * <pre>
     * <一句话功能简述>
     * 私有方法,获取泛型的TypeReference
     * <功能详细描述>
     * </pre>
     *
     * @author 姓名 工号
     * @version [版本号, 2018年6月11日]
     * @param clazz
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static <T> JavaType List(Class<?> clazz)
    {
        return objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
    }
}
