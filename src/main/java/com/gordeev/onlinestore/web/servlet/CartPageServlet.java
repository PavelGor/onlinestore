package com.gordeev.onlinestore.web.servlet;

import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.locator.ServiceLocator;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.security.Session;
import com.gordeev.onlinestore.service.ProductService;
import com.gordeev.onlinestore.web.servlet.utils.ServletUtils;
import com.gordeev.onlinestore.web.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartPageServlet extends HttpServlet {

    private ProductService productService = ProductService.getInstance();
    private SecurityService securityService = (SecurityService) ServiceLocator.getService("securityService");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Session session = securityService.getSession(request);
        List<Product> productList = session.getCart();

        Map<String, Object> pageVariables = new HashMap<>();
        response.setContentType("text/html;charset=utf-8");

        String userName = securityService.getName(request);
        if (userName != null){
            pageVariables.put("userName", userName);
        }

        if (productList == null || productList.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }

        pageVariables.put("productList", productList == null ? "" : productList);

        response.getWriter().println(PageGenerator.instance().getPage("cart.html", pageVariables));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = ServletUtils.createProductFromRequest(request);

        Session session = securityService.getSession(request);

        session.removeFromCard(product);

        response.sendRedirect("/cart");
    }
}
