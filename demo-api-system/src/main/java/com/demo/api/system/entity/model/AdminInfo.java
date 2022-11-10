package com.demo.api.system.entity.model;

import java.util.List;

public class AdminInfo {
    private String adminId;
    private int adminSeq;
    private int compSeq;
    private List<String> accMenu;
    private String localIp;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public int getAdminSeq() {
        return adminSeq;
    }

    public void setAdminSeq(int adminSeq) {
        this.adminSeq = adminSeq;
    }

    public int getCompSeq() {
        return compSeq;
    }

    public void setCompSeq(int compSeq) {
        this.compSeq = compSeq;
    }

    public List<String> getAccMenu() {
        return accMenu;
    }

    public void setAccMenu(List<String> accMenu) {
        this.accMenu = accMenu;
    }

    public String getLocalIp() {
        return localIp;
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }
}
