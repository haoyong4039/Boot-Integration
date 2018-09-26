package com.boot.integration.exeption;

public enum CustomCode
{
    // 成功返回
    SUCCESS(0),

    // 系统内部异常
    ERROR_SYSTEM(-1),

    // 权限不足
    ERROR_ACCESS_DENIED(101),

    // token异常
    ERROR_TOKEN(102),

    //用户不存在
    ERROR_USER_NOT_EXIST(1001),

    //json 参数非法
    ERROR_JSON_PARAMS_ILLEGAL(2001),

    //json 转换异常
    ERROR_JSON_CONVERT_FAIL(2002);

    private int value;

    CustomCode(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }
}
