package com.demo.common.exception;

import com.demo.common.model.CommonMessage;

public class CommonRuntimeException extends RuntimeException implements CommonException{
    private int errorCode = CommonMessage.CODE_ERROR_UNDEFINED;
    private static String DELIMITER = "|";
    private String[] arg;
    private String message = CommonMessage.ERRORMSG_UNDEFINED;

    public CommonRuntimeException() {

    }

    public CommonRuntimeException(Exception e) {
        super(e); // 모든 Exception을 RuntimeException으로 재정의
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
