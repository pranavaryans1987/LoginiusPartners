package com.loginius.loginiuspartners.Model.Project;

public class Project {
    String id,party,prj,date,amt,type;



    public Project(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getPrj() {
        return prj;
    }

    public void setPrj(String prj) {
        this.prj = prj;
    }

    public Project(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }
}
