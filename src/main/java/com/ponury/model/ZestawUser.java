package com.ponury.model;

public class ZestawUser extends Zestaw {
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ZestawUser(String data, String category, int grupa, String user) {
        super(data, category, grupa);
        this.user = user;
    }
}
