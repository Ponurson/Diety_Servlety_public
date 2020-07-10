package com.ponury.controller;

import com.ponury.model.Zakupy;
import com.ponury.model.ZakupyDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "View21", urlPatterns = "/view21")
public class View21 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] bought = request.getParameterValues("bought");
        HttpSession session = request.getSession();
        String user = session.getAttribute("username").toString();
        List<Zakupy> zakupyList = ZakupyDAO.findAllByUser(user);
        for (Zakupy z : zakupyList) {
            if (Arrays.stream(bought).
                    anyMatch(Integer.toString(z.getId())::equals)) {
                z.setBought(true);
            } else {
                z.setBought(false);
            }
            ZakupyDAO.update(z);
        }
        response.sendRedirect("/view2");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
