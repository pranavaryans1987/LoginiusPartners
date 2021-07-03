package com.loginius.loginiuspartners.Model.Stud;

public class Student {
    String id, nm, pnd, crs, type;
    int status;

    public Student(String id, String pnd, String type) {
        this.id = id;
        this.pnd = pnd;
        this.type = type;
    }

    public Student(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getPnd() {
        return pnd;
    }

    public void setPnd(String pnd) {
        this.pnd = pnd;
    }

    public String getCrs() {
        return crs;
    }

    public void setCrs(String crs) {
        this.crs = crs;
    }
}
