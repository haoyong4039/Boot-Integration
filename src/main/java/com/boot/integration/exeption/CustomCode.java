package com.boot.integration.exeption;

public enum CustomCode
{
    SUCCESS(0),

    ERROR_SYSTEM(-1),

    ERROR_ACCESS_DENIED(101),

    ERROR_TOKEN_EXPIRED(102),

    ERROR_BAD_CREDENTIALS(103),

    ERROR_ACCESS_REQUIRED(104),

    ERROR_USER_NOT_EXIST(1001);

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
