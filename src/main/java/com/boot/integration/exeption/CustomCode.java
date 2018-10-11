package com.boot.integration.exeption;

public enum CustomCode
{
    SUCCESS(0, "成功"),

    ERROR_SYSTEM(-1, "系统异常"),

    ERROR_ACCESS_DENIED(101, "权限不足"),

    ERROR_TOKEN(102, "token异常"),

    ERROR_USER_NOT_EXIST(1001, "用户不存在"),

    ERROR_USER_MORE(1002, "当前人数太多"),

    ERROR_TIME_OVER(1003, "库存不足"),

    ERROR_JSON_PARAMS_ILLEGAL(2001, "json参数非法"),

    ERROR_JSON_CONVERT_FAIL(2002, "json转换异常");

    private int value;

    private String message;

    CustomCode(int value, String message)
    {
        this.value = value;
        this.message = message;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
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
