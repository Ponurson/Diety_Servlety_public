package com.ponury.model;

public class User {
    private int id;
    private String passwd;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String username, String passwd) {
        this.username = username;
        this.passwd = passwd;

    }
}
