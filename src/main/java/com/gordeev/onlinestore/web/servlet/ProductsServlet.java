package com.gordeev.onlinestore.web.servlet;

import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.entity.User;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductsServlet extends HttpServlet {
    private ProductService productService;
    private SecurityService securityService;
    private TemplateEngine templateEngine = ThymeleafPageGenerator.getInstance().getTemplateEngine();

    public ProductsServlet(ProductService productService, SecurityService securityService) {
        this.productService = productService;
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale());
        List<Product> productList = productService.getAll();
        Map<String, Object> pageVariables = new HashMap<>();

        Optional<User> optionalUser = securityService.getUser(ServletUtils.getToken(request));
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            pageVariables.put("userRole", user.getRole().getName());
            pageVariables.put("userName", user.getUserName());
        }

        pageVariables.put("productList", productList);

        context.setVariables(pageVariables);

        String htmlString = templateEngine.process("products", context);
        response.getWriter().println(htmlString);

    }
}
