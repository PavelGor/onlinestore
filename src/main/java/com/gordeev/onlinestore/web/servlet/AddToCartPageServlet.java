package com.gordeev.onlinestore.web.servlet;

import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.locator.ServiceLocator;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.security.Session;
import com.gordeev.onlinestore.service.ProductService;
import com.gordeev.onlinestore.web.servlet.utils.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddToCartPageServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(AddToCartPageServlet.class);
    private SecurityService securityService = (SecurityService) ServiceLocator.getService(SecurityService.class);
    private ProductService productService = (ProductService) ServiceLocator.getService(ProductService.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idString = request.getParameter("id");

        if (idString!=null){
            Product product = productService.getById(Integer.parseInt(idString));
            Session session = securityService.getSession(ServletUtils.getToken(request));
            session.addToCart(product);

            LOG.info("User: " +session.getUser().getUserName() + " add product: " + product + " to his cart");
        }

        response.sendRedirect("/");
    }
}
