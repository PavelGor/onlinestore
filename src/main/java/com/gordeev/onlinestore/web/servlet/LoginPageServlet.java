package com.gordeev.onlinestore.web.servlet;

import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.locator.ServiceLocator;
import com.gordeev.onlinestore.service.UserService;
import com.gordeev.onlinestore.web.templater.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class LoginPageServlet extends HttpServlet {
    private UserService userService = (UserService) ServiceLocator.getService("userService");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.instance().getPage("login.html"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = userService.autenticate(login, password);

        if (user != null){
            String token = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("user-token", token);
            response.addCookie(cookie);
            response.sendRedirect("/products");
        } else {
            response.sendRedirect("/login");
        }
    }
}
