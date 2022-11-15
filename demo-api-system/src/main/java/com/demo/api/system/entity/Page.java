package com.demo.api.system.entity;

public class Page {
    private int page; //페이지 수
    private int size; //1페이지별 나와야 할 아이템 수
    private int totalCnt; //전체 아이템 수

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(int totalCnt) {
        this.totalCnt = totalCnt;
    }
}
