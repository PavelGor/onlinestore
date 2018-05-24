package com.gordeev.onlinestore.web.servlet;

import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.locator.ServiceLocator;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.security.Session;
import com.gordeev.onlinestore.service.ProductService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddToCartPageServlet extends HttpServlet {

    private SecurityService securityService = (SecurityService) ServiceLocator.getService("securityService");
    private ProductService productService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idString = request.getParameter("id");

        if (idString!=null){
            Product product = productService.getById(Integer.parseInt(idString));
            Session session = securityService.getSession(request);
            session.addToCart(product);
        }

        response.sendRedirect("/products");
    }
}
