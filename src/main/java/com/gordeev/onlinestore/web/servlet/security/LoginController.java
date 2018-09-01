package com.gordeev.onlinestore.web.servlet.security;

import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.security.Session;
import com.gordeev.onlinestore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/login")
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String getLoginPage(){
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String autenticateUser(@RequestParam String login,
                                @RequestParam String password,
                                HttpServletResponse response) {
        try {
            String token;
            User user = userService.autenticate(login, password);
            Optional<Session> optionalSession = securityService.getSession(user);

            if(optionalSession.isPresent()){
                token = optionalSession.get().getToken();
            } else {
                token = createSession(user);
            }

            Cookie cookie = new Cookie("user-token", token);
            cookie.setMaxAge(securityService.getSessionMaxLifeTime());
            response.addCookie(cookie);

            LOG.info("User: {} logged in", user.getUserName());
            return "redirect:/";
        } catch (SecurityException e) {
            return "redirect:/login";
        }
    }

    private String createSession(User user) {
        String token = UUID.randomUUID().toString();

        Session session = new Session();
        session.setUser(user);
        session.setToken(token);

        LocalDateTime time = LocalDateTime.now().plusSeconds(securityService.getSessionMaxLifeTime());
        session.setExpireTime(time);
        securityService.add(session);

        return token;
    }
}
