package com.gordeev.onlinestore.web.servlet;

import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.service.ProductService;
import com.gordeev.onlinestore.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductServlet extends HttpServlet {
    private ProductService productService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.instance().getPage("addProduct.html"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = createProductFromRequest(request);

        productService.add(product);

        response.sendRedirect("/products");
    }

    public Product createProductFromRequest(HttpServletRequest request) {
        String idString = request.getParameter("id");
        String name = request.getParameter("name");
        String priceString = request.getParameter("price");
        String description = request.getParameter("description");
        String imgLink = request.getParameter("imgLink");

        Product product = new Product();

        double price;
        if (request.getParameter("price").isEmpty()){
            price = 0;
        } else {
            price = Double.valueOf(priceString);
        }

        int id;
        if (idString!=null){
            id = Integer.parseInt(idString);
            product.setId(id);
        }

        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setImgLink(imgLink);
        product.setProductGroup("Сок"); //допилить работу с группой продукта

        return product;
    }
}
