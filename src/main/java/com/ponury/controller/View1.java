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

@WebServlet(name = "View1", urlPatterns = "/view1")
public class View1 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String skip = request.getParameter("skip");
        String[] dateCategory = skip.split(",");
        ModelUtils.skipMeal(dateCategory[1], dateCategory[0]);
        response.sendRedirect("/view1?date=" + dateCategory[0] + "&category=" + dateCategory[1]);
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
        String date = request.getParameter("date");
        String category = request.getParameter("category");

        List<DaniaWKoszyku> daniaWKoszykuList = DaniaWKoszykuDAO.findAllByUser(user);
        List<Integer> daniaWKoszykuIds = daniaWKoszykuList.stream()
                .map(daniaWKoszyku -> daniaWKoszyku.getIdZestawu())
                .collect(Collectors.toList());

        List<Zestaw> zestawList;
        if (user.equals("admin")) {
            zestawList = ZestawDAO.findAll();
        } else {
            List<ZestawUser> zestawUserList = ZestawUserDAO.findAll(user);
            zestawList = new ArrayList<>(zestawUserList);
        }

        List<HashMap<String, String>> dataForSwiper = new ArrayList<>();
        int centerSlide = 0;
        for (Zestaw zestaw : zestawList) {
            if (listOfDates.contains(zestaw.getData()) && zestaw.getCategory().equals(category)) {
                HashMap<String, String> nameIngRec = new HashMap<>();
                int grupa = zestaw.getGrupa();
                List<Danie> danieList = DanieDAO.readByGroupAndCategory(grupa, category);
                String recipe = "";
                String ingredients = "";
                String name = "";
                for (Danie danie :
                        danieList) {
                    if (danie.getGrupa() == grupa && danie.getCategory().equals(category)) {
                        name += danie.getName() + "\t";
                        ingredients += ControlerUtils.showIngredients(danie.getIngredients(), category, grupa, user)
                                .replaceAll("\n", "<br>") + "<br>";
                        recipe += danie.getRecipe().replaceAll("\n", "<br>") + "<br>";
                    }
                }
                nameIngRec.put("name", name);
                nameIngRec.put("ingredients", ingredients);
                nameIngRec.put("recipe", recipe);
                nameIngRec.put("data", zestaw.getData());
                if (daniaWKoszykuIds.contains(zestaw.getId())) {
                    nameIngRec.put("isInCart", "ok");
                }
                if (zestaw.getData().equals(date)) {
                    centerSlide = dataForSwiper.size();
                }
                dataForSwiper.add(nameIngRec);
            }
        }

        request.setAttribute("dataForSwiper", dataForSwiper);
        request.setAttribute("centerSlide", centerSlide);

        getServletContext().getRequestDispatcher("/WEB-INF/view/view1.jsp").forward(request, response);
    }
}
