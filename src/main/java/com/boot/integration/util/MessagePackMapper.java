package com.boot.integration.util;

import com.boot.integration.exeption.CustomCode;
import com.boot.integration.exeption.CustomException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <pre>
 * <一句话功能简述>
 * 处理协议
 * <功能详细描述>
 * </pre>
 * 
 * @author xiongwei
 * @version [版本号, 2018年7月6日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MessagePackMapper
{
    
    /* ======================================================================== */
    /* * * * * * * * * * * * * * * Public Fields (公共属性) * * * * * * * * * */
    /* ======================================================================== */
    
    private static ObjectMapper mapper = new ObjectMapper(new MessagePackFactory());
    
    /* ======================================================================== */
    /*
     * * * * * * * * * * * * * * * Private Fields (私有属性) * * * * * * * * * / /*
     * ========================================================================
     */
    
    /* ======================================================================== */
    /*
     * * * * * * * * * * * * * * * Construct Methods (构造方法) * * * * * * * / /*
     * ========================================================================
     */
    
    /* ======================================================================== */
    /*
     * * * * * * * * * * * * * * * Public Methods (公有方法) * * * * * * * * * / /*
     * ========================================================================
     */
    /**
     * 
     * <pre>
     * <一句话功能简述>
     * 对象转byte数组
     * <功能详细描述>
     * </pre>
     * 
     * @author 姓名 工号
     * @version [版本号, 2018年6月11日]
     * @param obj
     * @return
     * @throws CustomException
     * @see [类、类#方法、类#成员]
     */
    public static <T> byte[] toBytes(T obj)
        throws CustomException
    {
        try
        {
            return mapper.writeValueAsBytes(obj);
        }
        catch (JsonProcessingException e)
        {
            throw new CustomException(CustomCode.ERROR_SYSTEM.getValue());
        }
        
    }
    
    /**
     * 
     * <pre>
     * <一句话功能简述>
     * byte转list集合
     * <功能详细描述>
     * </pre>
     * 
     * @author 姓名 工号
     * @version [版本号, 2018年6月11日]
     * @param bytes
     * @param clazz
     * @return
     * @throws CustomException
     * @see [类、类#方法、类#成员]
     */
    public static <T> List<T> toList(byte[] bytes, Class<T> clazz)
        throws CustomException
    {
        List<T> list = null;
        try
        {
            list = mapper.readValue(bytes, MessagePackMapper.List(clazz));
        }
        catch (IOException e)
        {
            list = new ArrayList<>();
            throw new CustomException(CustomCode.ERROR_SYSTEM.getValue());
        }
        return list;
    }
    
    /**
     * 
     * <pre>
     * <一句话功能简述>
     * byte转指定对象
     * <功能详细描述>
     * </pre>
     * 
     * @author 姓名 工号
     * @version [版本号, 2018年6月11日]
     * @param bytes
     * @param clazz
     * @return
     * @throws CustomException
     * @see [类、类#方法、类#成员]
     */
    public static <T> T toObject(byte[] bytes, Class<T> clazz)
        throws CustomException
    {
        T value = null;
        try
        {
            value = mapper.readValue(bytes, clazz);
        }
        catch (IOException e)
        {
            throw new CustomException(CustomCode.ERROR_SYSTEM.getValue());
        }
        return value;
    }
    
    /* ======================================================================== */
    /*
     * * * * * * * * * * * * * * * Private Methods (私有方法) * * * * * ** * * / /*
     * ========================================================================
     */
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
        return mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
    }
}
