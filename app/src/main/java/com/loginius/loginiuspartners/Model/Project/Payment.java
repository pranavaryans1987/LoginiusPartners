package com.loginius.loginiuspartners.Model.Project;

public class Payment {
    String id,prjnm,devamt,devnm,pndamt,type;
    int status;

    public Payment(String type) {
        this.type = type;
    }

    public Payment(String id, String devamt, String type) {
        this.id = id;
        this.devamt = devamt;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrjnm() {
        return prjnm;
    }

    public void setPrjnm(String prjnm) {
        this.prjnm = prjnm;
    }

    public String getDevamt() {
        return devamt;
    }

    public void setDevamt(String devamt) {
        this.devamt = devamt;
    }

    public String getDevnm() {
        return devnm;
    }

    public void setDevnm(String devnm) {
        this.devnm = devnm;
    }

    public String getPndamt() {
        return pndamt;
    }

    public void setPndamt(String pndamt) {
        this.pndamt = pndamt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
