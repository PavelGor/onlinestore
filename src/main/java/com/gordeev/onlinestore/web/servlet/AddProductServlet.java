package com.gordeev.onlinestore.web.servlet;

import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.service.AppContext;
import com.gordeev.onlinestore.service.ProductService;
import com.gordeev.onlinestore.web.servlet.util.ServletUtils;
import com.gordeev.onlinestore.web.templater.ThymeleafPageGenerator;
import org.springframework.context.ApplicationContext;
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
    private TemplateEngine templateEngine = ThymeleafPageGenerator.getInstance().getTemplateEngine();
    private ApplicationContext applicationContext = AppContext.getInstance();
    private SecurityService securityService = applicationContext.getBean(SecurityService.class);
    private ProductService productService = applicationContext.getBean(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebContext context = new WebContext(request, response, request.getServletContext(),
                request.getLocale());
        Map<String, Object> pageVariables = new HashMap<>();
        response.setContentType("text/html;charset=utf-8");

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
