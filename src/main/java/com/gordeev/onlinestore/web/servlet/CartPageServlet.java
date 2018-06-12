package com.gordeev.onlinestore.web.servlet;

import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.locator.ServiceLocator;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.security.Session;
import com.gordeev.onlinestore.web.servlet.utils.ServletUtils;
import com.gordeev.onlinestore.web.templater.ThymeleafPageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartPageServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(CartPageServlet.class);
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
        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale());
        Session session = securityService.getSession(ServletUtils.getToken(request));
        List<Product> productList = session.getCart();

        Map<String, Object> pageVariables = new HashMap<>();

        User user = securityService.getUser(ServletUtils.getToken(request));
        if (user != null){
            pageVariables.put("userName", user.getUserName());
            LOG.info("User: " + user.getUserName() + " enter to his cart");
        }

        pageVariables.put("productList", productList);

        context.setVariables(pageVariables);

        String htmlString = templateEngine.process("cart", context);
        response.getWriter().println(htmlString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = ServletUtils.getProductFromRequest(request);

        Session session = securityService.getSession(ServletUtils.getToken(request));

        session.removeFromCard(product);

        response.sendRedirect("/cart");
    }
}
