package com.gordeev.onlinestore.web.servlet;

import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.security.Session;
import com.gordeev.onlinestore.service.ProductService;
import com.gordeev.onlinestore.web.servlet.util.ControllerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {
    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private SecurityService securityService;
    @Autowired
    private ProductService productService;


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    protected String showAddProductView(@CookieValue("user-token") String token, Model model){
        Optional<User> optionalUser = securityService.getUser(token);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            model.addAttribute("userRole", user.getRole().getName());
            model.addAttribute("userName", user.getUserName());
        }
        return "addProduct";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    protected String addProduct(HttpServletRequest request) {
        Product product = ControllerUtils.getProductFromRequest(request); //TODO do it as JSON if can here
        productService.add(product);
        return "redirect:/";
    }


    @RequestMapping(value = "/edit/*", method = RequestMethod.GET)
    protected String showEditProductView(HttpServletRequest request, @CookieValue("user-token") String token, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.getById(id);
        model.addAttribute("product", product);

        Optional<User> optionalUser = securityService.getUser(token);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            model.addAttribute("userRole", user.getRole().getName());
            model.addAttribute("userName", user.getUserName());
        }

        return "edit";
    }

    @RequestMapping(value = "/edit/*", method = RequestMethod.POST)
    protected String editProduct(HttpServletRequest request) {
        Product product = ControllerUtils.getProductFromRequest(request);
        productService.edit(product);
        return "redirect:/";
    }


    @RequestMapping(value = "/delete/*", method = RequestMethod.GET)
    protected String showDeleteView(HttpServletRequest request, @CookieValue("user-token") String token, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.getById(id);
        model.addAttribute("product", product);

        Optional<User> optionalUser = securityService.getUser(token);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            model.addAttribute("userRole", user.getRole().getName());
            model.addAttribute("userName", user.getUserName());
        }

        return "delete";
    }

    @RequestMapping(value = "/delete/*", method = RequestMethod.POST)
    protected String deleteProduct(HttpServletRequest request) {
        Product product = ControllerUtils.getProductFromRequest(request);
        productService.delete(product);
        return "redirect:/";
    }


    @RequestMapping(value = "/cart/*", method = RequestMethod.POST)
    protected String addProductToCart(@CookieValue("user-token") String token, @RequestParam String id) {
        if (id!=null){
            Product product = productService.getById(Integer.parseInt(id));
            Optional<Session> optionalSession = securityService.getSession(token);
            if (optionalSession.isPresent()){
                optionalSession.get().addToCart(product);
                LOG.info("User: {} add product: {} to his cart", optionalSession.get().getUser().getUserName(), product);
            }
        }
        return "redirect:/";
    }
}
