package com.boot.integration.util.data;

import com.boot.integration.exeption.CustomCode;
import com.boot.integration.exeption.CustomException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * <pre>
 * <一句话功能简述>
 * JSON转化的工具类
 * <功能详细描述>
 * </pre>
 *
 * @author haoyong
 */
public class ObjectMapperUtils
{
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * <pre>
     * <一句话功能简述>
     * 将字符串解析成Map对象
     * <功能详细描述>
     * </pre>
     *
     * @param str json字符串
     * @return Map<String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               String>
     * @throws CustomException
     */
    public static Map<String, String> convertStringToMap(String str)
        throws CustomException
    {
        if (StringUtils.isEmpty(str))
        {
            throw new CustomException(CustomCode.ERROR_JSON_PARAMS_ILLEGAL);
        }
        try
        {
            return objectMapper.readValue(str, Map.class);
        }
        catch (Exception e)
        {
            throw new CustomException(CustomCode.ERROR_JSON_CONVERT_FAIL);
        }
    }

    /**
     * <pre>
     * <一句话功能简述>
     * 将字符串解析成Map对象
     * <功能详细描述>
     * </pre>
     *
     * @param str json字符串
     * @return Map<String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               String>
     * @throws CustomException
     * @see [类、类#方法、类#成员]
     */
    public static Map<String, Object> convertStringToMapObject(String str)
        throws CustomException
    {
        if (StringUtils.isEmpty(str))
        {
            throw new CustomException(CustomCode.ERROR_JSON_PARAMS_ILLEGAL);
        }

        try
        {
            return objectMapper.readValue(str, Map.class);
        }
        catch (Exception e)
        {
            throw new CustomException(CustomCode.ERROR_JSON_CONVERT_FAIL);
        }
    }

    /**
     * <pre>
     * <一句话功能简述>
     * 将字符串解析成Object对象
     * <功能详细描述>
     * </pre>
     *
     * @param str       json字符串
     * @param valueType 转换成对象
     * @return Object
     * @throws CustomException
     */
    public static Object convertStringToObject(String str, Class<?> valueType)
        throws CustomException
    {
        if (StringUtils.isEmpty(str) || valueType == null)
        {
            throw new CustomException(CustomCode.ERROR_JSON_PARAMS_ILLEGAL);
        }
        try
        {
            return objectMapper.readValue(str, valueType);
        }
        catch (Exception e)
        {
            throw new CustomException(CustomCode.ERROR_JSON_CONVERT_FAIL);
        }
    }

    /**
     * <pre>
     * <一句话功能简述>
     * 将Object转换成字符串
     * <功能详细描述>
     * </pre>
     *
     * @param obj 对象
     * @return String
     * @throws CustomException
     */
    public static String convertObjectToString(Object obj)
        throws CustomException
    {
        if (obj == null)
        {
            throw new CustomException(CustomCode.ERROR_JSON_PARAMS_ILLEGAL);
        }
        try
        {
            return objectMapper.writeValueAsString(obj);
        }
        catch (Exception e)
        {
            throw new CustomException(CustomCode.ERROR_JSON_CONVERT_FAIL);
        }
    }

    public static void main(String[] args)
    {
        String data = "[{\"menuId\":\"50\",\"opId\":\"5001,5002,5003\"}]";
        JSONArray myJsonArray = JSONArray.fromObject(data);
        for(int i=0;i<myJsonArray.size();i++)
        {
            System.out.println(myJsonArray.getJSONObject(i).get("menuId"));
            System.out.println(myJsonArray.getJSONObject(i).get("opId"));
        }
    }
}
