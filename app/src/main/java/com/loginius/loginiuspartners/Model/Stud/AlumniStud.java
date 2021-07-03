package com.loginius.loginiuspartners.Model.Stud;

public class AlumniStud {
    String id, name, crs, mob, type;
    int status;

    public AlumniStud(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public AlumniStud(String type) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCrs() {
        return crs;
    }

    public void setCrs(String crs) {
        this.crs = crs;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }
}
