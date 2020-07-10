package com.ponury.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/view1", "/view2", "/view21"})
public class AuthFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        Object username = session.getAttribute("username");
        if (username != null) {
            chain.doFilter(req, resp);
        }else {
            HttpServletResponse response = (HttpServletResponse) resp;
            response.sendRedirect("/index.jsp");
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
