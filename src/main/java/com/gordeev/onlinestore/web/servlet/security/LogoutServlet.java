package com.gordeev.onlinestore.web.servlet.security;

import com.gordeev.onlinestore.context.Context;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.security.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class LogoutServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(LogoutServlet.class);
    private SecurityService securityService = Context.getContext(SecurityService.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    String token = cookie.getValue();
                    Optional<Session> optionalSession = securityService.getSession(token);
                    if (optionalSession.isPresent()){
                        LOG.info("User: " + optionalSession.get().getUser().getUserName() + " logout");
                        securityService.removeSession(token);
                    }
                    break;
                }
            }
        }
        response.sendRedirect("/");
    }
}
