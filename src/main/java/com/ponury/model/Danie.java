package com.ponury.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Danie {
    private int id;
    private String name;
    private String recipe;
    private String category;
    private int grupa;
    private Map<String,Integer> ingredients;

    public Danie(String name, String recipe, String category, int grupa,Map<String, Integer> ingredients) {
        this.name = name;
        this.recipe = recipe;
        this.category = category;
        this.grupa = grupa;
        this.ingredients = ingredients;
    }

    public Danie(String name, String recipe, String category, int grupa, String ingredients) {
        HashMap<String, Integer> ingredientsMap = new HashMap<>();
        String ingredientsNoParenthesis = ingredients.replaceAll("\\(.+\\)\\s", "");
        String[] lines = ingredientsNoParenthesis.split("\n");
        for (String s:lines) {
            String[] valueKey = s.split(" g ");
            try {
                ingredientsMap.put(valueKey[1], Integer.parseInt(valueKey[0]));
            } catch (Exception e){

            }
        }
        this.name = name;
        this.recipe = recipe;
        this.category = category;
        this.grupa = grupa;
        this.ingredients = ingredientsMap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getGrupa() {
        return grupa;
    }

    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }
    public String getIngredientsString() {
        Set<String> keySet = ingredients.keySet();
        String out = "";
        for (String key: keySet) {
            out += ingredients.get(key)+" g "+key+"\n";
        }
        return out;
    }

    public void setIngredients(Map<String, Integer> ingredients) {
        this.ingredients = ingredients;
    }
}
