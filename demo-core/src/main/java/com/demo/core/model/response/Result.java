package com.demo.core.model.response;

import com.demo.core.model.ErrorCode;

/**
 * 응답 결과 정보
 */
public class Result<E> {
    private int errorCode;
    private String errorMessage;
    private E data;

    public Result() {
        this.data = null;
        this.errorCode = ErrorCode.OK.getValue();
        this.errorMessage = ErrorCode.OK.getDescription();
    }

    public Result(E data) {
        this.data = data;
        this.errorCode = ErrorCode.OK.getValue();
        this.errorMessage = ErrorCode.OK.getDescription();
    }

    public Result(String errorMessage) {
        this.errorMessage = errorMessage;
        this.data = null;
    }

    public Result(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = null;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
