package com.gordeev.onlinestore.web.servlet.security;

import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.security.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/exit")
public class LogoutController {
    private static final Logger LOG = LoggerFactory.getLogger(LogoutController.class);

    @Autowired
    private SecurityService securityService;

    @RequestMapping(method = RequestMethod.POST)
    protected void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    String token = cookie.getValue();
                    Optional<Session> optionalSession = securityService.getSession(token);
                    if (optionalSession.isPresent()){
                        LOG.info("User: {} logout", optionalSession.get().getUser().getUserName());
                        securityService.removeSession(token);
                    }
                    break;
                }
            }
        }
        response.sendRedirect("/");
    }
}
