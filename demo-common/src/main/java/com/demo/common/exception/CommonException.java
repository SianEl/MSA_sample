package com.demo.common.exception;

public interface CommonException {
    public int getErrorCode();

    public void setErrorCode(int errorCode);

    public String getMessage();

    public void setMessage(String message);
}
