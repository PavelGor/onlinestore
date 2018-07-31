package com.gordeev.onlinestore.web.servlet;

import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.security.Session;
import com.gordeev.onlinestore.web.servlet.util.ServletUtils;
import com.gordeev.onlinestore.web.templater.ThymeleafPageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CartPageServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(CartPageServlet.class);
    private TemplateEngine templateEngine = ThymeleafPageGenerator.getInstance().getTemplateEngine();

    private SecurityService securityService;

    public CartPageServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale());
        Map<String, Object> pageVariables = new HashMap<>();

        Optional<Session> optionalSession = securityService.getSession(ServletUtils.getToken(request));
        if (optionalSession.isPresent()){
            List<Product> productList = optionalSession.get().getCart();
            pageVariables.put("productList", productList);
        }

        Optional<User> optionalUser = securityService.getUser(ServletUtils.getToken(request));
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            pageVariables.put("userRole", user.getRole().getName());
            pageVariables.put("userName", user.getUserName());
            LOG.info("User: " + user.getUserName() + " enter to his cart");
        }

        context.setVariables(pageVariables);

        String htmlString = templateEngine.process("cart", context);
        response.getWriter().println(htmlString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = ServletUtils.getProductFromRequest(request);

        Optional<Session> optionalSession = securityService.getSession(ServletUtils.getToken(request));

        optionalSession.ifPresent(session -> session.removeFromCard(product));

        response.sendRedirect("/cart");
    }
}
