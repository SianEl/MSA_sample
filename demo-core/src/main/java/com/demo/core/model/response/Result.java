package com.demo.core.model.response;

import com.demo.core.model.ErrorCode;

/**
 * 응답 결과 정보
 */
public class Result {
    private int errorCode;
    private String errorMessage;

    public Result() {
        this.errorCode = ErrorCode.OK.getValue();
        this.errorMessage = ErrorCode.OK.getDescription();
    }

    public Result(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Result(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
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
}
