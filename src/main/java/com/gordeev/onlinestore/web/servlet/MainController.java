package com.gordeev.onlinestore.web.servlet;

import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private SecurityService securityService;
    @Autowired
    private ProductService productService;

    @RequestMapping(value = {"/","/products*"}, method = RequestMethod.GET)
    protected String showProducts(@CookieValue(value = "user-token", required = false) String token,
                                  @RequestParam(required = false) String pageNumber,
                                  @RequestParam(required = false) String productsOnPage,
                                  Model model){

        int intPageId = pageNumber != null? Integer.parseInt(pageNumber) : productService.getDefaultPageNumber();
        model.addAttribute("pageNumber", intPageId);

        int intProductsOnPage = productsOnPage != null? Integer.parseInt(productsOnPage) : productService.getProductsOnPage();
        model.addAttribute("productsOnPage", intProductsOnPage);

        model.addAttribute("pageCount", (int) Math.ceil(productService.getProductsQuantity()/(intProductsOnPage/1.0)));

        int from = intProductsOnPage * (intPageId-1) + 1;
        List<Product> productList = productService.getAll(intProductsOnPage, from);
        model.addAttribute("productList", productList);

        Optional<User> optionalUser = securityService.getUser(token);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            model.addAttribute("userRole", user.getRole().getName());
            model.addAttribute("userName", user.getUserName());
        }

        return "products";
    }
}
