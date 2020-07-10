package com.ponury.model;

public class Zakupy {
    private String name;
    private int id;
    private int amount;
    private boolean bought;
    private String altMass;
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAltMass() {
        return altMass;
    }

    public void setAltMass(String altMass) {
        this.altMass = altMass;
    }

    public Zakupy(String name, int amount, boolean bought, String user) {
        this.name = name;
        this.amount = amount;
        this.bought = bought;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }
}
