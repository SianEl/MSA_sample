package com.demo.api.system.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class Admin {
    private int adminSeq;   //관리자 고유번호
    private int compSeq;    //업체 고유번호
    private String adminId; //관리자 아이디
    private String adminPw; //관리자 비밀번호
    private String adminStatCd; //관리자 상태코드
    private String adminTypeCd; //관리자 유형코드
    private String lastLoginDt; //최종로그인일시
    private String pwChgDt;  //비밀번호 변경일시
    private String regDt; //등록일시
    private String modDt; //수정일시


}
