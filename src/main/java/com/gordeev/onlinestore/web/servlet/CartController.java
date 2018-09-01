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

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {
    private static final Logger LOG = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private SecurityService securityService;
    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public String showCart(@CookieValue("user-token") String token, Model model) {
        Optional<Session> optionalSession = securityService.getSession(token);
        if (optionalSession.isPresent()){
            List<Product> productList = optionalSession.get().getCart();
            model.addAttribute("productList", productList);
        }

        Optional<User> optionalUser = securityService.getUser(token);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            model.addAttribute("userRole", user.getRole().getName());
            model.addAttribute("userName", user.getUserName());
            LOG.info("User: " + user.getUserName() + " enter to his cart");
        }

        return "cart";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String removeProductFromCart(HttpServletRequest request, @CookieValue("user-token") String token) {
        Product product = ControllerUtils.getProductFromRequest(request);
        Optional<Session> optionalSession = securityService.getSession(token);
        optionalSession.ifPresent(session -> session.removeFromCard(product));

        return "redirect:/cart";
    }
}
