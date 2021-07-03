package com.loginius.loginiuspartners.Model.Develop;

public class DevBody {
    int status;
    String snm,scno,type;

    public DevBody(String snm, String scno, String type) {
        this.snm = snm;
        this.scno = scno;
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
