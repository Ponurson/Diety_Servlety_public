package com.ponury.controller;

import com.ponury.model.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "Login", urlPatterns = "/login")
public class Login extends HttpServlet {
    private final static String USER = "";
    private final static String PASSWORD = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(3600 * 24 * 30);
        if (USER.equals(user) && PASSWORD.equals(pass)) {
            session.setAttribute("username", user);
            DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            response.sendRedirect("/view1?date=" + sdf.format(LocalDateTime.now()) + "&category=Obiad");
        } else if ("nowy".equals(user) && "użytkownik".equals(pass)) {
            response.sendRedirect("/register");
        } else if (UserDAO.autorization(user, pass) != null) {
            session.setAttribute("username", user);
            DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            response.sendRedirect("/view1?date=" + sdf.format(LocalDateTime.now()) + "&category=Obiad");
        } else {
            request.setAttribute("wrongLogin", "Podano błedne dane logowania");
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
