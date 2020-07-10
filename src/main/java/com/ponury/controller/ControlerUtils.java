package com.ponury.controller;

import com.ponury.model.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ControlerUtils {
    public static String showIngredients(Map<String, Integer> ingredients, String category, int grupa, String user) {
        String out = "";
        if ("Śniadanie".equals(category) && grupa == 0) {
            out += "na dwa dni dla dwóch osób\n";
        } else {
            List<Setting> settings = SettingDAO.findAllByUser(user);
            Optional<Setting> osobyNaPosilek = settings.stream()
                    .filter(setting -> setting.getName().equals(category))
                    .findFirst();
            Optional<Setting> ileDniPodRzad = settings.stream()
                    .filter(setting -> setting.getName().equals("ileDniPodRzad"))
                    .findFirst();
            String s = ileDniPodRzad.get().getValue().equals("1") ? "dzień" : "dni";
            String s2 = osobyNaPosilek.get().getValue().equals("1") ? "osoby" : "osób";
            out += "na " + ileDniPodRzad.get().getValue() + " " + s + " dla " + osobyNaPosilek.get().getValue() + " " + s2 + "\n";
        }

        Set<String> keySet = ingredients.keySet();
        int multiplier = ModelUtils.multiplier(category, grupa, user);
        for (String key : keySet) {
            String altMass = "";
            try {
                Przelicznik przelicznik = PrzelicznikDAO.readByIngredient(key);
                altMass = " (" + Math.round(ingredients.get(key) * multiplier * przelicznik.getConverter()) + " " + przelicznik.getUnit() + ") ";
            } catch (Exception e) {
            }
            out += ingredients.get(key) * multiplier + " g " + altMass + key + "\n";
        }
        return out;
    }

    public static String listaZakupowPrzelicznik(Zakupy zakupy) {
        int amount = zakupy.getAmount();
        String name = zakupy.getName();
        String altMass = "";
        try {
            Przelicznik przelicznik = PrzelicznikDAO.readByIngredient(name);
            altMass = " (" + Math.round(amount * przelicznik.getConverter()) + " " + przelicznik.getUnit() + ") ";
        } catch (Exception e) {
        }
        return altMass;
    }

}
