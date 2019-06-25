package com.boot.integration.util.web;

import com.boot.integration.exeption.CustomCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class BaseResponse
{

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(BaseResponse.class);

    /**
     * <pre>
     * <一句话功能简述>
     * 封装响应数据
     * <功能详细描述>
     * </pre>
     *
     * @param retCode 业务响应码
     * @param retMsg  业务响应码说明
     * @param retData 业务相应数据
     */
    public static Map<String, Object> getResponseMap(String retCode, String retMsg, Object retData)
    {
        logger.info("[retCode:{}]-[retMsg:{}]-[retData:{}]", retCode, retMsg, retData);

        if (StringUtils.isEmpty(retCode))
        {
            retCode = CustomCode.ERROR_SYSTEM.getValue();
            retMsg = CustomCode.ERROR_SYSTEM.getMessage();
            retData = "";
        }

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("retCode", retCode);
        responseMap.put("retMsg", retMsg);
        responseMap.put("retData", retData);

        return responseMap;
    }
}

