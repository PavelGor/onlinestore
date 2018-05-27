package com.gordeev.onlinestore.web.servlet.security;

import com.gordeev.onlinestore.locator.ServiceLocator;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.security.Session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LogoutServlet extends HttpServlet {
    private SecurityService securityService = (SecurityService) ServiceLocator.getService("securityService");
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    List sessionList = securityService.getSessionList();
                    Session session = securityService.getSession(cookie.getValue());
                    sessionList.remove(session);
                    break;
                }
            }
        }
        response.sendRedirect("/products");
    }
}
