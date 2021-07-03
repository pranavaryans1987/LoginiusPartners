package com.loginius.loginiuspartners.Model.Develop;

public class DevList {
    String dnm,type;

    public DevList(String type) {
        this.type = type;
    }

    public String getDnm() {
        return dnm;
    }

    public void setDnm(String dnm) {
        this.dnm = dnm;
    }
}
