package com.boot.integration.exeption;

public class CustomException extends Exception
{

    private int value;

    public CustomException()
    {
        super();
    }

    public CustomException(CustomCode customCode)
    {
        super(customCode.getMessage());
        this.value = customCode.getValue();
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
