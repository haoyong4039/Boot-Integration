package com.boot.integration.exeption;

public class CustomException extends Exception
{

    private String value;

    public CustomException()
    {
        super();
    }

    public CustomException(CustomCode customCode)
    {
        super(customCode.getMessage());
        this.value = customCode.getValue();
    }

    public CustomException(String value, String message)
    {
        super(message);
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

}
