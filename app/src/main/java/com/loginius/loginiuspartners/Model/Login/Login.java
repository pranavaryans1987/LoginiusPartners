package com.loginius.loginiuspartners.Model.Login;

public class Login {
    String client, psd, id, type;
    int status;

    public Login(String client, String psd, String type) {
        this.client = client;
        this.psd = psd;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
