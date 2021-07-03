package com.loginius.loginiuspartners.Model.Course;

public class CourseNameBody {
    String crsnm, crsfee, type, snm, scno, sfcno, rvf;
    int status;

    public CourseNameBody(String crsnm, String crsfee, String snm, String scno, String sfcno, String rvf, String type) {
        this.crsnm = crsnm;
        this.crsfee = crsfee;
        this.type = type;
        this.snm = snm;
        this.scno = scno;
        this.sfcno = sfcno;
        this.rvf = rvf;
    }

    public CourseNameBody(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCrsnm() {
        return crsnm;
    }

    public void setCrsnm(String crsnm) {
        this.crsnm = crsnm;
    }

    public String getCrsfee() {
        return crsfee;
    }

    public void setCrsfee(String crsfee) {
        this.crsfee = crsfee;
    }

}
