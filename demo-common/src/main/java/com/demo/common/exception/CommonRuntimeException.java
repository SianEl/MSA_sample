package com.demo.common.exception;

import com.demo.common.model.ExceptionMessage;

public class CommonRuntimeException extends RuntimeException implements CommonException{

    private int errorCode = ExceptionMessage.CODE_ERROR_UNDEFINED;
    private String message = ExceptionMessage.ERRORMSG_UNDEFINED;

    public CommonRuntimeException() {

    }

    public CommonRuntimeException(Exception e) {
        super(e); // 모든 Exception 을 RuntimeException 으로 재정의
    }

    public CommonRuntimeException(String message) {
        this.message = message;
    }

    public CommonRuntimeException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
