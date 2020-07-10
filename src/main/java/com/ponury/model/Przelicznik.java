package com.ponury.model;

public class Przelicznik {
    private int id;
    private String ingredient;
    private String unit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private double converter;

    public Przelicznik(String ingredient, String unit, double converter) {
        this.ingredient = ingredient;
        this.unit = unit;
        this.converter = converter;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getConverter() {
        return converter;
    }

    public void setConverter(double converter) {
        this.converter = converter;
    }
}
