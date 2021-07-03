package com.loginius.loginiuspartners.Model.Project;

public class ProjectBody {
    int status;
    String pnm, cpnm, cno, prjnm, prjamt, devamt, date, revamt, devnm, pnd, type, id;

    //    getT9okE6npr2jLi1st
    public ProjectBody(String pnm, String cpnm, String cno, String prjnm, String prjamt, String devamt, String date, String revamt, String devnm, String type) {
        this.pnm = pnm;
        this.cpnm = cpnm;
        this.cno = cno;
        this.prjnm = prjnm;
        this.prjamt = prjamt;
        this.devamt = devamt;
        this.date = date;
        this.revamt = revamt;
        this.devnm = devnm;
        this.type = type;
    }

    public ProjectBody(String id, String pnd, String type) {
        this.pnd = pnd;
        this.type = type;
        this.id = id;
    }

    public ProjectBody(String id, String type) {
        this.type = type;
        this.id = id;
    }

    public ProjectBody(String type) {
        this.type = type;
    }

    public String getPnm() {
        return pnm;
    }

    public void setPnm(String pnm) {
        this.pnm = pnm;
    }

    public String getCpnm() {
        return cpnm;
    }

    public void setCpnm(String cpnm) {
        this.cpnm = cpnm;
    }

    public String getPrjnm() {
        return prjnm;
    }

    public void setPrjnm(String prjnm) {
        this.prjnm = prjnm;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPnd() {
        return pnd;
    }

    public void setPnd(String pnd) {
        this.pnd = pnd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
