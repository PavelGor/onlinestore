package com.gordeev.onlinestore.web.servlet.security;

import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.locator.ServiceLocator;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.security.Session;
import com.gordeev.onlinestore.service.UserService;
import com.gordeev.onlinestore.web.templater.PageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class LoginServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);
    private UserService userService = (UserService) ServiceLocator.getService("userService");
    private SecurityService securityService = (SecurityService) ServiceLocator.getService("securityService");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(PageGenerator.instance().getPage("login.html"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        int MAX_AGE_SESSION = 300;

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = userService.autenticate(login, password);

        if (user != null){
            String token = UUID.randomUUID().toString();

            Session session = new Session();
            session.setUser(user);
            session.setToken(token);

            LocalDateTime time = LocalDateTime.now().plusSeconds(MAX_AGE_SESSION);
            session.setExpiredTime(time);
            securityService.getSessionList().add(session);

            Cookie cookie = new Cookie("user-token", token);
            cookie.setMaxAge(MAX_AGE_SESSION);
            response.addCookie(cookie);
            response.sendRedirect("/products");
            LOG.info("User: " + user.getUserName() + " login");
        } else {
            response.sendRedirect("/login");
        }
    }
}
