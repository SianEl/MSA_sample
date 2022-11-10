package com.demo.common.exception;

public class CustomException extends CommonRuntimeException{

    public CustomException(String message) {
        super(message);
    }

    public CustomException(int errorCode, String message) {
        super(errorCode, message);
    }

    public CustomException() {
        super();
    }
}
