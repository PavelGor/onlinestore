package com.gordeev.onlinestore.web.servlet;

import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.security.Session;
import com.gordeev.onlinestore.service.ProductService;
import com.gordeev.onlinestore.web.servlet.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AddToCartPageServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(AddToCartPageServlet.class);

    private ProductService productService;
    private SecurityService securityService;

    public AddToCartPageServlet(ProductService productService, SecurityService securityService) {
        this.productService = productService;
        this.securityService = securityService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idString = request.getParameter("id");

        if (idString!=null){
            Product product = productService.getById(Integer.parseInt(idString));
            Optional<Session> optionalSession = securityService.getSession(ServletUtils.getToken(request));
            if (optionalSession.isPresent()){
                optionalSession.get().addToCart(product);
                LOG.info("User: {} add product: {} to his cart", optionalSession.get().getUser().getUserName(), product);
            }
        }

        response.sendRedirect("/");
    }
}
