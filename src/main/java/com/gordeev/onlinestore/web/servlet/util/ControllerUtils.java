package com.gordeev.onlinestore.web.servlet.util;

import com.gordeev.onlinestore.entity.Product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class ControllerUtils {
    public static Product getProductFromRequest(HttpServletRequest request) {
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
        product.setPriceUah(price);
        product.setDescription(description);
        product.setImgLink(imgLink);

        return product;
    }
    public static String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
