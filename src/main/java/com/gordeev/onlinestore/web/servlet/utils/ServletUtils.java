package com.gordeev.onlinestore.web.servlet.utils;

import com.gordeev.onlinestore.entity.Product;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {
    public static Product createProductFromRequest(HttpServletRequest request) {
        String idString = request.getParameter("id");
        String name = request.getParameter("name");
        String priceString = request.getParameter("price");
        String description = request.getParameter("description");
        String imgLink = request.getParameter("imgLink");

        Product product = new Product();

        double price = priceString.isEmpty() ? 0 : Double.valueOf(priceString);

        int id;
        if (idString!=null){
            id = Integer.parseInt(idString);
            product.setId(id);
        }

        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setImgLink(imgLink);
        product.setProductGroup("Сок"); //TODO: допилить работу с группой продукта

        return product;
    }
}
