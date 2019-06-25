package com.boot.integration.exeption;

public enum CustomCode
{
    SUCCESS("0", "请求成功"),

    ERROR_SYSTEM("-1", "系统异常"),

    ERROR_ACCESS_DENIED("101", "权限不足"),

    ERROR_TOKEN("102", "token异常"),

    ERROR_USER_NOT_EXIST("1001", "用户不存在"),

    ERROR_USER_MORE("1002", "当前人数太多"),

    ERROR_STOCK_NULL("1003", "库存不足"),

    ERROR_REPEAT("1004", "请勿重复提交"),

    ERROR_LOCK_KEY_NULL("1005", "LOCK KEY IS NULL"),

    ERROR_JSON_PARAMS_ILLEGAL("2001", "json参数非法"),

    ERROR_JSON_CONVERT_FAIL("2002", "json转换异常"),

    ERROR_MSG_PACK_CONVERT_FAIL("2003", "msgPack转换异常");

    private String value;

    private String message;

    CustomCode(String value, String message)
    {
        this.value = value;
        this.message = message;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
