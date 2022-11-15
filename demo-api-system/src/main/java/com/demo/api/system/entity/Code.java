package com.demo.api.system.entity;

public class Code extends Page{
    private String codeGrp; //코드그룹
    private String code;    //상세코드
    private String codeNm;  //코드이름
    private boolean useYn;  //사용여부
    private int dispNo;     //전시순서
    private String regDt;   //등록일자
    private String modDt;   //수정일자

    public String getCodeGrp() {
        return codeGrp;
    }

    public void setCodeGrp(String codeGrp) {
        this.codeGrp = codeGrp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeNm() {
        return codeNm;
    }

    public void setCodeNm(String codeNm) {
        this.codeNm = codeNm;
    }

    public boolean isUseYn() {
        return useYn;
    }

    public void setUseYn(boolean useYn) {
        this.useYn = useYn;
    }

    public int getDispNo() {
        return dispNo;
    }

    public void setDispNo(int dispNo) {
        this.dispNo = dispNo;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getModDt() {
        return modDt;
    }

    public void setModDt(String modDt) {
        this.modDt = modDt;
    }
}
