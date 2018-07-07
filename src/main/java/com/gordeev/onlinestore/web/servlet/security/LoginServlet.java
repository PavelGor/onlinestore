package com.gordeev.onlinestore.web.servlet.security;

import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.locator.ServiceLocator;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.security.Session;
import com.gordeev.onlinestore.service.UserService;
import com.gordeev.onlinestore.web.templater.ThymeleafPageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class LoginServlet extends HttpServlet {
    private int MAX_AGE_SESSION = 300;
    private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);
    private UserService userService = (UserService) ServiceLocator.getService(UserService.class);
    private SecurityService securityService = (SecurityService) ServiceLocator.getService(SecurityService.class);
    private TemplateEngine templateEngine;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ThymeleafPageGenerator thymeleafPageGenerator = new ThymeleafPageGenerator();
        templateEngine = thymeleafPageGenerator.getTemplateEngine(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebContext context = new WebContext(request, response, request.getServletContext(),
                request.getLocale());
        String htmlString = templateEngine.process("login", context);
        response.getWriter().println(htmlString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        try {
            String token;
            User user = userService.autenticate(login, password);
            Session session = securityService.getSession(user);

            if(session != null){
                token = session.getToken();
            } else {
                token = createSession(user);
            }

            Cookie cookie = new Cookie("user-token", token);
            cookie.setMaxAge(MAX_AGE_SESSION);
            response.addCookie(cookie);
            response.sendRedirect("/");
            LOG.info("User: " + user.getUserName() + " logged in");
        } catch (IllegalAccessException e) {
            response.sendRedirect("/login");
            e.printStackTrace();
        }
    }

    private String createSession(User user) {
        String token = UUID.randomUUID().toString();

        Session session = new Session();
        session.setUser(user);
        session.setToken(token);

        LocalDateTime time = LocalDateTime.now().plusSeconds(MAX_AGE_SESSION);
        session.setExpiredTime(time);
        securityService.add(session);

        return token;
    }
}
