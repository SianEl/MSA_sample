package com.demo.common.model;

public class ExceptionMessage {
    // error 없을때
    public static final int CODE_NO_ERROR = 0;
    public static final String MSG_OK = "OK";

    // db 오류
    public static final int CODE_ERROR_DB = -2;
    public static final String ERRORMSG_DB_ERROR = "DB 오류가 발생했습니다.\n관리자에게 문의하세요."; // "DB_ERROR";

    public static final int CODE_INCORRECT_PASSWORD = 640;
    public static final String ERRORMSG_INCORRECT_PASSWORD =
            "아이디 또는 비밀번호를 다시 확인하세요.\n등록되지 않은 아이디이거나, 아이디 또는 비밀번호를 잘못 입력하셨습니다.";

    public static final int CODE_UNRESISTED_ID = 640;
    public static final String ERRORMSG_UNRESISTED_ID =
            "아이디 또는 비밀번호를 다시 확인하세요.\n등록되지 않은 아이디이거나, 아이디 또는 비밀번호를 잘못 입력하셨습니다.";

    public static final int CODE_LOGIN_ERROR = 640;
    public static final String ERRORMSG_LOGIN_ERROR = "로그인 되지 않았습니다.";

    public static final int CODE_INACCESSIBLE = 403;
    public static final String ERRORMSG_INACCESSIBLE = "접근 권한이 없습니다.";

    // 알수 없는 오류
    public static final int CODE_ERROR_UNDEFINED = -3;
    public static final String ERRORMSG_UNDEFINED =
            "정상처리 되지 않았습니다.<br>중복된 데이터 이거나 입력데이터에 문제가 있을 수 있습니다.\n문제가 지속되는 경우 관리자에게 연락하시기 바랍니다. "; // "UNDEFINED";
}
