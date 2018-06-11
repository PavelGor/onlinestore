package com.gordeev.onlinestore.web.servlet;

import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.locator.ServiceLocator;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.service.ProductService;
import com.gordeev.onlinestore.web.servlet.utils.ServletUtils;
import com.gordeev.onlinestore.web.templater.PageGenerator;
import com.gordeev.onlinestore.web.templater.ThymeleafAppUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.TemplateSpec;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsServlet extends HttpServlet {

    private ProductService productService = (ProductService) ServiceLocator.getService(ProductService.class);
    private SecurityService securityService = (SecurityService) ServiceLocator.getService(SecurityService.class);

    private ServletContextTemplateResolver resolver;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        resolver = new ServletContextTemplateResolver(getServletContext());
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setPrefix("/webapp/templates/");
        resolver.setSuffix(".html");
        resolver.setCacheable(true);
        resolver.setCacheTTLMs(60000L);
        resolver.setCharacterEncoding("utf-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> productList = productService.getAll();
        Map<String, Object> pageVariables = new HashMap<>();

        User user = securityService.getUser(ServletUtils.getToken(request));
        if (user != null){
            pageVariables.put("userName", user.getUserName());
        }

        pageVariables.put("productList", productList);

//        response.getWriter().println(PageGenerator.instance().getPage("products.html", pageVariables));


        WebContext context = new WebContext(request, response, request.getServletContext(),
                request.getLocale());
        context.setVariable("currentDate", new Date());
        context.setVariable("welcome.msg", "Welcome to Thymeleaf World!");
        TemplateEngine templateEngine = new TemplateEngine();//ThymeleafAppUtil.getTemplateEngine();
        templateEngine.setTemplateResolver(resolver);
        String htmlString = templateEngine.process("welcome", context);
        response.getWriter().println(htmlString);

    }
}
