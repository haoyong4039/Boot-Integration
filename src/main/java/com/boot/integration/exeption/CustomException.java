package com.boot.integration.exeption;

public class CustomException extends Exception {

    private int value;

    public CustomException() {
        super();
    }

    public CustomException(int value) {
        super(String.valueOf(value));
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
