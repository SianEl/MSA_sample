package com.demo.api.system.entity;

public class AdminMenu {
    private Integer adminMenuSeq;       //관리자메뉴 고유번호
    private int prntAdminMenuSeq;   //상위 관리자메뉴 고유번호
    private String menuNm;          //메뉴이름
    private String menuUrl;         //메뉴URL
    private String menuPath;        //메뉴경로
    private int menuLv;             //메뉴레벨
    private int dispNo;             //전시순서
    private Boolean dispYn;         //전시여부
    private Boolean useYn;          //사용여부
    private String regDt;           //등록일시
    private String modDt;           //수정일시

    public Integer getAdminMenuSeq() {
        return adminMenuSeq;
    }

    public void setAdminMenuSeq(int adminMenuSeq) {
        this.adminMenuSeq = adminMenuSeq;
    }

    public int getPrntAdminMenuSeq() {
        return prntAdminMenuSeq;
    }

    public void setPrntAdminMenuSeq(int prntAdminMenuSeq) {
        this.prntAdminMenuSeq = prntAdminMenuSeq;
    }

    public String getMenuNm() {
        return menuNm;
    }

    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }

    public int getMenuLv() {
        return menuLv;
    }

    public void setMenuLv(int menuLv) {
        this.menuLv = menuLv;
    }

    public int getDispNo() {
        return dispNo;
    }

    public void setDispNo(int dispNo) {
        this.dispNo = dispNo;
    }

    public Boolean isDispYn() {
        return dispYn;
    }

    public void setDispYn(Boolean dispYn) {
        this.dispYn = dispYn;
    }

    public Boolean isUseYn() {
        return useYn;
    }

    public void setUseYn(Boolean useYn) {
        this.useYn = useYn;
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
