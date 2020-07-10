package com.ponury.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ModelUtils {
    private static final String[] CATEGORIES = {"Śniadanie", "II Śniadanie", "Lunch", "Obiad"};

    public static int multiplier(String category, int group, String user) {
        List<Setting> settings = SettingDAO.findAllByUser(user);
        int multi = 1;
        if ("Śniadanie".equals(category) && group == 0) {
            return 4;
        }
        for (Setting s : settings) {
            if (s.getName().equals(category) || s.getName().equals("ileDniPodRzad")) {
                multi *= Integer.parseInt(s.getValue());
            }
        }
        return multi;
    }

    public static Boolean weekendCheck(String data) {
        //TODO sprawdzać z listą świąt też
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Poland"));
        calendar.setTime(date);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        } else {
            return false;
        }
    }

    public static void skipMeal(String category, String dateString) {
        //Pobieramy wszystkie posiłki
        //Szukamy takich z odpowiednią kategorią i datą równą i póżniejszą niż date
        //szeregujemy je date'em
        List<Zestaw> zestawList = ZestawDAO.findAllByDateAndCategory(dateString, category);
        // dla każdego poza pierwszym robimy set date na wcześniejszy
        Zestaw zestawLast = zestawList.get(zestawList.size() - 1);
        ZestawDAO.create(new Zestaw(addOneDay(zestawLast.getData()),
                zestawLast.getCategory(),
                zestawLast.getGrupa()));
        for (int i = zestawList.size() - 1; i > 0; i--) {
            zestawList.get(i).setGrupa(zestawList.get(i - 1).getGrupa());
            ZestawDAO.update(zestawList.get(i));
        }
        zestawList.get(0).setGrupa(1);
        ZestawDAO.update(zestawList.get(0));
        // dla pierwszego robimy set group na 0
        // dopisujemy do listy posiłków w bazie w grupie 0 danie nie jedzone
        //wszystkie poza ostatnim robimy update w bazie, ostatni robimy create
        // dopisujemy tak żeby nie można było skipować posiłku juz zeskipowanego
    }

    public static String addOneDay(String oldDate) {
        //Specifying date format that matches the given date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            //Setting the date to the given date
            c.setTime(sdf.parse(oldDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DAY_OF_MONTH, 1);
        String newDate = sdf.format(c.getTime());
        return newDate;
    }

    public static void createShoppingList(String[] groupCat, String user) {
        ArrayList<Zakupy> zakupyBroadList = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();
        List<Setting> settingList = SettingDAO.findAllByUser(user);
        for (String gC : groupCat) {
            String[] split = gC.split(";");
            List<Danie> daniaNaPosilek = DanieDAO.readByGroupAndCategory(Integer.parseInt(split[0]), split[1]);
            List<Setting> dlaIluOsob = settingList.stream()
                    .filter(setting -> setting.getName().equals(split[1]))
                    .collect(Collectors.toList());
            int multiplier = Integer.parseInt(dlaIluOsob.get(0).getValue());
            for (Danie danie : daniaNaPosilek) {
                Map<String, Integer> ingredients = danie.getIngredients();
                Set<String> keySet = ingredients.keySet();
                for (String key : keySet) {
                    Zakupy zakupy = new Zakupy(key, ingredients.get(key) * multiplier, false, user);
                    zakupyBroadList.add(zakupy);
                }
            }
            dates.add(split[2]);
        }
        ArrayList<Zakupy> zakupyListSetLike = new ArrayList<>();
        for (Zakupy z : zakupyBroadList) {
            Boolean alreadyThereFlag = false;
            for (int i = 0; i < zakupyListSetLike.size(); i++) {
                Zakupy base = zakupyListSetLike.get(i);
                if (base.getName().equals(z.getName())) {
                    base.setAmount(base.getAmount() + z.getAmount());
                    zakupyListSetLike.set(i, base);
                    alreadyThereFlag = true;
                    break;
                }
            }
            if (!alreadyThereFlag) {
                zakupyListSetLike.add(z);
            }
        }
        for (Zakupy z : zakupyListSetLike) {
            ZakupyDAO.create(z);
        }
        List<String> collect = dates.stream()
                .sorted()
                .collect(Collectors.toList());
        String stopDate = collect.get(collect.size() - 1);
        System.out.println(stopDate);
        List<Setting> doKiedy = settingList.stream()
                .filter(setting -> setting.getName().equals("doKiedy"))
                .collect(Collectors.toList());
        if (doKiedy.size() > 0) {
            doKiedy.get(0).setValue(stopDate);
            SettingDAO.update(doKiedy.get(0));
        } else {
            SettingDAO.create(new Setting("doKiedy", stopDate, user));
        }
    }


    public static void createShoppingList(String startDate, String stopDate, String user) {
        String date = startDate;
        ArrayList<String> dates = new ArrayList<>();
        dates.add(date);
        while (!date.equals(stopDate)) {
            date = addOneDay(date);
            dates.add(date);
        }
        ArrayList<Zakupy> zakupyBroadList = new ArrayList<>();
        for (String cat : CATEGORIES) {
            Zestaw zestawForTest = null;
            for (int i = 0; i < dates.size(); i++) {
                date = dates.get(i);
                Zestaw zestaw;
                if (user.equals("admin")) {
                    zestaw = ZestawDAO.readByDateAndCategory(date, cat);
                } else {
                    zestaw = ZestawUserDAO.readByDateAndCategory(date, cat, user);
                }
                int specialMultiplier = 1;
                if (dates.size() % 2 == 0 &&
                        i == dates.size() - 1 &&
                        zestaw.getGrupa() != zestawForTest.getGrupa() &&
                        1 != zestawForTest.getGrupa() &&
                        0 != zestawForTest.getGrupa()) {
                    specialMultiplier = 2;
                }
                List<Danie> daniaNaPosilek = DanieDAO.readByGroupAndCategory(zestaw.getGrupa(), cat);
                int multiplier = multiplier(cat, zestaw.getGrupa(), user) / 2;
                for (Danie danie : daniaNaPosilek) {
                    Map<String, Integer> ingredients = danie.getIngredients();
                    Set<String> keySet = ingredients.keySet();
                    for (String key : keySet) {
                        Zakupy zakupy = new Zakupy(key, ingredients.get(key) * multiplier * specialMultiplier, false, user);
                        zakupyBroadList.add(zakupy);
                    }
                }
                zestawForTest = zestaw;
            }
        }
        ArrayList<Zakupy> zakupyListSetLike = new ArrayList<>();
        for (Zakupy z : zakupyBroadList) {
            Boolean alreadyThereFlag = false;
            for (int i = 0; i < zakupyListSetLike.size(); i++) {
                Zakupy base = zakupyListSetLike.get(i);
                if (base.getName().equals(z.getName())) {
                    base.setAmount(base.getAmount() + z.getAmount());
                    zakupyListSetLike.set(i, base);
                    alreadyThereFlag = true;
                    break;
                }
            }
            if (!alreadyThereFlag) {
                zakupyListSetLike.add(z);
            }
        }
        for (Zakupy z : zakupyListSetLike) {
            ZakupyDAO.create(z);
        }
        List<Setting> settingList = SettingDAO.findAllByUser(user);
        List<Setting> doKiedy = settingList.stream()
                .filter(setting -> setting.getName().equals("doKiedy"))
                .collect(Collectors.toList());
        if (doKiedy.size() > 0) {
            doKiedy.get(0).setValue(stopDate);
            SettingDAO.update(doKiedy.get(0));
        } else {
            SettingDAO.create(new Setting("doKiedy", stopDate, user));
        }

    }

    public static void createDishesOnList(String[] shoppingCart, String user) {
        for (String sC : shoppingCart) {
            String[] split = sC.split(";");
            DaniaWKoszykuDAO.create(new DaniaWKoszyku(Integer.parseInt(split[3]), user));
        }
    }

}

