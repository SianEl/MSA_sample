package com.demo.core.model.response;

import com.demo.core.model.ErrorCode;

import java.util.List;

/**
 * 리스트 응답 결과 정보
 */
public class ListResult<T> {
    private int totalCount;         //총 개수
    private List<T> list = null;           // 리스트 형태 결과물
    private int errorCode;          // 에러 코드
    private String errorMessage;    // 에러 메세지

    public ListResult() {

    }

    public ListResult(List<T> list) {
        this.list = list;
        this.errorCode = ErrorCode.OK.getValue();
        this.errorMessage = ErrorCode.OK.getDescription();
    }

    public ListResult(List<T> list, int totalCount) {
        this.list = list;
        this.totalCount = totalCount;
        this.errorCode = ErrorCode.OK.getValue();
        this.errorMessage = ErrorCode.OK.getDescription();
    }

    public ListResult(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
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
