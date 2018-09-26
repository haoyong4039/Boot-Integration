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
 * <pre>
 * <一句话功能简述>
 * msgPack序列化反序列化
 * <功能详细描述>
 * </pre>
 *
 * @author haoyong
 */
public class MessagePackMapper
{

    private static ObjectMapper mapper = new ObjectMapper(new MessagePackFactory());

    /**
     * <pre>
     * <一句话功能简述>
     * 对象转byte数组
     * <功能详细描述>
     * </pre>
     *
     * @param obj
     * @throws CustomException
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
     * <pre>
     * <一句话功能简述>
     * byte数组转指定对象
     * <功能详细描述>
     * </pre>
     *
     * @param bytes
     * @param clazz
     * @throws CustomException
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

    /**
     * <pre>
     * <一句话功能简述>
     * byte转list集合
     * <功能详细描述>
     * </pre>
     *
     * @param bytes
     * @param clazz
     * @throws CustomException
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
     * <pre>
     * <一句话功能简述>
     * 私有方法,获取泛型的TypeReference
     * <功能详细描述>
     * </pre>
     *
     * @param clazz
     */
    private static <T> JavaType List(Class<?> clazz)
    {
        return mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
    }
}
