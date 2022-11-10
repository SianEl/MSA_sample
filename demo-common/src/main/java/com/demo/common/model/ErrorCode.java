package com.demo.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 *
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    OK(0, "정상"),
    LOGIN_INIT(11, "초기화 된 계정"),
    INACCESSIBLE(403, "올바르지 않은 접근 (메뉴 접근 권한)"),
    CSV(604, "CSV 파일 생성 오류"),
    EXCEL_PARSE(605, "엑셀 파싱 오류"),
    NEED_LOGIN(640, "미로그인 오류"),
    INCORRECT_LOGIN(641, "아이디 또는 패스워드 오류"),
    FAIL_LOGIN(642, "로그인 실패 횟수 초과"),
    DEACTIVE_STAT(643, "비활성 계정상태"),
    NO_DATA(644, "입력 데이터 없음"),
    INCORRECT_DATA(645, "올바르지 않은 데이터"),
    DUPLICATED(646, "중복 데이터"),
    UNAUTHORIZED(647, "인증 정보 없음"),
    UNDEFINED(99, "알 수 없는 오류");
    private final int value;
    private final String description;
}
