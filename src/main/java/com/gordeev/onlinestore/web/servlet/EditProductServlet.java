package com.gordeev.onlinestore.web.servlet;

import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.service.ProductService;
import com.gordeev.onlinestore.web.servlet.utils.ServletUtils;
import com.gordeev.onlinestore.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditProductServlet extends HttpServlet {

    private ProductService productService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.getById(id);

        pageVariables.put("product", product);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.instance().getPage("edit.html", pageVariables));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = ServletUtils.createProductFromRequest(request);

        productService.edit(product);

        response.sendRedirect("/products");
    }
}
