package com.ponury.controller;

import com.ponury.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterFormView", urlPatterns = "/register")
public class RegisterFormView extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserDAO.create(new User(password, username));
        String sniadanie = request.getParameter("sniadanie");
        SettingDAO.create(new Setting("Śniadanie", sniadanie, username));
        String lunch = request.getParameter("lunch");
        SettingDAO.create(new Setting("Lunch", lunch, username));
        String obiad = request.getParameter("obiad");
        SettingDAO.create(new Setting("Obiad", obiad, username));
        String kolacja = request.getParameter("kolacja");
        SettingDAO.create(new Setting("II Śniadanie", kolacja, username));
        String ileDniPodRzad = request.getParameter("ileDniPodRzad");
        SettingDAO.create(new Setting("ileDniPodRzad", ileDniPodRzad, username));
        ZestawUserDAO.fillZestawy(username, Integer.parseInt(ileDniPodRzad));
        response.sendRedirect("/index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/view/register_form.jsp").forward(request, response);
    }
}
