package com.gordeev.onlinestore.web.servlet;

import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.locator.ServiceLocator;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.service.ProductService;
import com.gordeev.onlinestore.web.servlet.util.ServletUtils;
import com.gordeev.onlinestore.web.templater.ThymeleafPageGenerator;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AddProductServlet extends HttpServlet {
    private ProductService productService = (ProductService) ServiceLocator.getService(ProductService.class);
    private SecurityService securityService = (SecurityService) ServiceLocator.getService(SecurityService.class);
    private TemplateEngine templateEngine = ThymeleafPageGenerator.getInstance().getTemplateEngine();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebContext context = new WebContext(request, response, request.getServletContext(),
                request.getLocale());
        Map<String, Object> pageVariables = new HashMap<>();

        Optional<User> optionalUser = securityService.getUser(ServletUtils.getToken(request));
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            pageVariables.put("userRole", user.getRole().getName());
            pageVariables.put("userName", user.getUserName());
        }
        context.setVariables(pageVariables);

        String htmlString = templateEngine.process("addProduct", context);
        response.getWriter().println(htmlString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = ServletUtils.getProductFromRequest(request);

        productService.add(product);

        response.sendRedirect("/");
    }


}
