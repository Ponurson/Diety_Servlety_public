package com.ponury.controller;

import com.ponury.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "View2", urlPatterns = "/view2")
public class View2 extends HttpServlet {
    private static final String[] CATEGORIES = {"Śniadanie", "Lunch", "Obiad", "II Śniadanie"};

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user = session.getAttribute("username").toString();
        String[] shoppingCart = request.getParameterValues("shoppingCart");
        ZakupyDAO.deleteAll(user);
        ModelUtils.createShoppingList(shoppingCart, user);
        DaniaWKoszykuDAO.deleteAll(user);
        ModelUtils.createDishesOnList(shoppingCart, user);
        response.sendRedirect("/view2");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> listOfDates = (ArrayList<String>) request.getAttribute("listOfDates");
        if (listOfDates == null) {
            listOfDates = new ArrayList<>();
            DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime dateForMeals = LocalDateTime.now();
            for (int plusDays = -7; plusDays < 7; plusDays++) {
                LocalDateTime dateForMeals2 = dateForMeals.plusDays(plusDays);
                listOfDates.add(sdf.format(dateForMeals2));
            }
            request.setAttribute("listOfDates", listOfDates);
        }
        HttpSession session = request.getSession();
        String user = session.getAttribute("username").toString();
        List<Zakupy> zakupyList = ZakupyDAO.findAllByUser(user);
        ArrayList<Zakupy> nieKupione = new ArrayList<>();
        ArrayList<Zakupy> kupione = new ArrayList<>();
        for (int i = 0; i < zakupyList.size(); i++) {
            Zakupy z = zakupyList.get(i);
            z.setAltMass(ControlerUtils.listaZakupowPrzelicznik(z));
            if (z.isBought()) {
                kupione.add(z);
            } else {
                nieKupione.add(z);
            }
        }
        nieKupione.addAll(kupione);
        request.setAttribute("listaZakupow", nieKupione);

        List<Setting> settingList = SettingDAO.findAllByUser(user);
        List<Setting> doKiedy = settingList.stream()
                .filter(setting -> setting.getName().equals("doKiedy"))
                .collect(Collectors.toList());
        if (doKiedy.size() > 0) {
            request.setAttribute("doKiedy", doKiedy.get(0).getValue());
        }


        List<Zestaw> zestawList;
        if (user.equals("admin")) {
            zestawList = ZestawDAO.findAll();
        } else {
            List<ZestawUser> zestawUserList = ZestawUserDAO.findAll(user);
            zestawList = new ArrayList<>(zestawUserList);
        }

        ArrayList<String> finalListOfDates = listOfDates;
        ArrayList<List<HashMap<String, String>>> daniaForZakupy = new ArrayList<>();
        for (String c : CATEGORIES) {
            List<HashMap<String, String>> out = zestawList.stream()
                    .filter(zestaw -> finalListOfDates.contains(zestaw.getData()) && zestaw.getCategory().equals(c))
                    .map(zestaw -> {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("data", zestaw.getData());
                        map.put("group", String.valueOf(zestaw.getGrupa()));
                        map.put("cat", c);
                        map.put("id", String.valueOf(zestaw.getId()));
                        map.put("name", DanieDAO.readByGroupAndCategory(zestaw.getGrupa(), c).get(0).getName());
                        return map;
                    }).collect(Collectors.toList());
            daniaForZakupy.add(out);
        }
        ArrayList<List<HashMap<String, String>>> outer = new ArrayList<>();
        for (int i = 0; i < daniaForZakupy.get(0).size(); i++) {
            ArrayList<HashMap<String, String>> inner = new ArrayList<>();
            for (int j = 0; j < daniaForZakupy.size(); j++) {
                inner.add(daniaForZakupy.get(j).get(i));
            }
            outer.add(inner);
        }

        request.setAttribute("daniaForZakupy", outer);


        getServletContext().getRequestDispatcher("/WEB-INF/view/view2.jsp").forward(request, response);
    }
}
