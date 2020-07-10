package com.ponury.model;

public class Zestaw {
    private int id;
    private String data;
    private String category;
    private int grupa;


    public Zestaw(String data,String category, int grupa) {
        this.data = data;
        this.grupa = grupa;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getGrupa() {
        return grupa;
    }

    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }
}
