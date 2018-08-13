package com.boot.integration.conf.security;

public class MyUsernameNotFoundException extends RuntimeException {

    public MyUsernameNotFoundException(String msg) {
        super(msg);
    }
}
