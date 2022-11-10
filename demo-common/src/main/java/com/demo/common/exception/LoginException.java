package com.demo.common.exception;

public class LoginException extends CommonRuntimeException{
    public LoginException(String message) {
        super(message);
    }

    public LoginException(int errorCode, String message) {
        super(errorCode, message);
    }

    public LoginException() {
        super();
    }
}
