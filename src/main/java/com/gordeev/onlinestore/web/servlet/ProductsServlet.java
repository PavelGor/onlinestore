package com.gordeev.onlinestore.web.servlet;

import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.locator.ServiceLocator;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.service.ProductService;
import com.gordeev.onlinestore.web.servlet.utils.ServletUtils;
import com.gordeev.onlinestore.web.templater.ThymeleafPageGenerator;
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

public class ProductsServlet extends HttpServlet {

    private ProductService productService = (ProductService) ServiceLocator.getService(ProductService.class);
    private SecurityService securityService = (SecurityService) ServiceLocator.getService(SecurityService.class);

    private TemplateEngine templateEngine;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
//        ThymeleafPageGenerator thymeleafPageGenerator = new ThymeleafPageGenerator();
//        templateEngine = thymeleafPageGenerator.getTemplateEngine(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale());
        List<Product> productList = productService.getAll();
        Map<String, Object> pageVariables = new HashMap<>();


        ThymeleafPageGenerator thymeleafPageGenerator = new ThymeleafPageGenerator();
        templateEngine = thymeleafPageGenerator.getTemplateEngine(getServletContext());


        User user = securityService.getUser(ServletUtils.getToken(request));
        if (user != null){
            pageVariables.put("userRole", user.getRole().toString());
            pageVariables.put("userName", user.getUserName());
        }

        pageVariables.put("productList", productList);

        context.setVariables(pageVariables);

        String htmlString = templateEngine.process("products", context);
        response.getWriter().println(htmlString);

    }
}
