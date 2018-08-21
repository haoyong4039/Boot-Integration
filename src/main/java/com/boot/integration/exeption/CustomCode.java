package com.boot.integration.exeption;

public enum CustomCode
{

    SUCCESS(0),

    ERROR_SYSTEM(-1),

    ERROR_USER_NOT_EXIST(101);

    private int value;

    CustomCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
