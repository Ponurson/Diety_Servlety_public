package com.ponury.model;

public class DaniaWKoszyku {
    private int id;
    private int idZestawu;
    private String user;

    public DaniaWKoszyku(int idZestawu, String user) {
        this.idZestawu = idZestawu;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdZestawu() {
        return idZestawu;
    }

    public void setIdZestawu(int idZestawu) {
        this.idZestawu = idZestawu;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
