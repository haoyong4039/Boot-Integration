package com.boot.integration.controller;

import com.alibaba.fastjson.JSONObject;
import com.boot.integration.exeption.CustomException;
import com.boot.integration.model.User;
import com.boot.integration.util.JacksonMapper;
import com.boot.integration.util.MessagePackMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/object")
public class ObjectController
{

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * <pre>
     * <一句话功能简述>
     * 通过JSONObject直接获取属性值
     * <功能详细描述>
     * </pre>
     *
     * @author haoyong
     */
    @RequestMapping(value = "/json", method = RequestMethod.POST)
    public void readValue(@RequestBody JSONObject jsonObject)
    {
        logger.info("[username] - {},[password] - {}", jsonObject.getString("username"), jsonObject.getString("password"));

        try
        {
            //字符串转Object对象
            User user = (User)JacksonMapper.convertStringToObject(String.valueOf(jsonObject),User.class);
            logger.info("[string to obj] - {}", user);

            //对象转byte数组
            byte[] bytes = MessagePackMapper.toBytes(user);
            logger.info("[obj to bytes] - {}", bytes);

            //byte数组转对象
            user  = MessagePackMapper.toObject(bytes,User.class);
            logger.info("[bytes to obj] - {}", user);
        }
        catch (CustomException e)
        {
            logger.error("Custom Error !!!", e);
        }
    }

    /**
     * <pre>
     * <一句话功能简述>
     * 反射
     * <功能详细描述>
     * </pre>
     *
     * @author haoyong
     */
    @RequestMapping(value = "/reflect", method = RequestMethod.GET)
    public void reflect()
    {
        try
        {
            //获取class对象
            Class<?> clazz = new User().getClass();

            //获取该类中的所有属性
            Field[] fields = clazz.getDeclaredFields();
            //遍历所有的属性
            System.out.println("************************Class属性**************************");
            for (Field field : fields)
            {
                System.out.println(field);
                System.out.println("修饰符：" + Modifier.toString(field.getModifiers()));
                System.out.println("类型：" + field.getType());
                System.out.println("属性名：" + field.getName());
                System.out.println();
            }
            System.out.println("**********************************************************");

            //获取该类中所有的方法
            Method[] methods = clazz.getDeclaredMethods();
            //遍历方法
            System.out.println("************************Class方法**************************");
            for (Method method : methods)
            {
                // 打印方法签名
                System.out.println(method);
                System.out.println("修饰符：" + Modifier.toString(method.getModifiers()));
                System.out.println("方法名：" + method.getName());
                System.out.println("返回类型：" + method.getReturnType());
                // 获取方法的参数对象
                Class<?>[] clazzes = method.getParameterTypes();
                for (Class<?> class1 : clazzes)
                {
                    System.out.println("参数类型：" + class1);
                }
                System.out.println();
            }
            System.out.println("**********************************************************");

            // 通过Class对象创建实例
            User user = (User)clazz.newInstance();
            // username(Field)对象，以便下边重新设置它的值
            Field username = clazz.getDeclaredField("username");
            //私有属性授权
            username.setAccessible(true);
            // 设置username的值为”Mr.hao“
            username.set(user, "Mr.hao");
            // 通过Class对象获取名为”setPassword“，参数类型为String的方法(Method)对象
            Method setPassword = clazz.getMethod("setPassword", String.class);
            // 调用finishTask方法
            setPassword.invoke(user, "123456");

            System.out.println(user);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
