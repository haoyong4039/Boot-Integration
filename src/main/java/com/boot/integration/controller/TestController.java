package com.boot.integration.controller;

import com.alibaba.fastjson.JSONObject;
import com.boot.integration.exeption.CustomException;
import com.boot.integration.model.User;
import com.boot.integration.util.data.ObjectMapperUtils;
import com.boot.integration.util.data.MessagePackUtils;
import com.boot.integration.util.http.HttpCovert;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@RestController
@RequestMapping("/test")
public class TestController
{

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final ObjectMapper objectMapper = new ObjectMapper();

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
    public void readValue(@RequestBody JSONObject jsonObject) throws CustomException, IOException
    {
        logger.info("username:{}, password:{}", jsonObject.getString("username"), jsonObject.getString("password"));

        //转为对象
        User user = (User)ObjectMapperUtils.convertStringToObject(String.valueOf(jsonObject), User.class);

        byte[] strBytes = String.valueOf(jsonObject).getBytes();
        logger.info("string序列化:{}", strBytes);
        logger.info("string反序列化:{}", new String(strBytes));

        byte[] objMapperBytes = objectMapper.writeValueAsBytes(user);
        logger.info("objectMapper序列化:{}", objMapperBytes);
        logger.info("objectMapper反序列化:{}", objectMapper.readValue(objMapperBytes, User.class));

        byte[] msgPackBytes = MessagePackUtils.toBytes(user);
        logger.info("msgPack序列化:{}", msgPackBytes);
        logger.info("msgPack反序列化:{}", MessagePackUtils.toObject(msgPackBytes, User.class));
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

    /**
     * <pre>
     * <一句话功能简述>
     * 将流转为字符串
     * <功能详细描述>
     * </pre>
     *
     * @author haoyong
     */
    @RequestMapping(value = "/input/covert", method = RequestMethod.POST)
    public void inputCovert(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            System.out.println(HttpCovert.convertStreamToMsg(request.getInputStream()));
        }
        catch (CustomException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
