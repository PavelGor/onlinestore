//package com.gordeev.onlinestore;
//
//import com.gordeev.onlinestore.security.SecurityService;
//import com.gordeev.onlinestore.service.ProductService;
//import com.gordeev.onlinestore.service.UserService;
//import com.gordeev.onlinestore.web.filter.AdminSecurityFilter;
//import com.gordeev.onlinestore.web.filter.ContentTypeFilter;
//import com.gordeev.onlinestore.web.filter.UserSecurityFilter;
//import com.gordeev.onlinestore.web.servlet.*;
//import com.gordeev.onlinestore.web.servlet.assets.AssetsServlet;
//import com.gordeev.onlinestore.web.servlet.security.LoginServlet;
//import com.gordeev.onlinestore.web.servlet.security.LogoutServlet;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.eclipse.jetty.server.Server;
//import org.eclipse.jetty.servlet.FilterHolder;
//import org.eclipse.jetty.servlet.ServletContextHandler;
//import org.eclipse.jetty.servlet.ServletHolder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.DispatcherType;
//import java.util.EnumSet;
//
//public class Main {
//    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
//
//    public static void main(String[] args) throws Exception {
//
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("context.xml");
//
//        UserService userService = applicationContext.getBean(UserService.class);
//        SecurityService securityService = applicationContext.getBean(SecurityService.class);
//        ProductService productService = applicationContext.getBean(ProductService.class);
//
//        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//
//        context.addServlet(new ServletHolder(new ProductsServlet(productService, securityService)), "/");
//        context.addServlet(new ServletHolder(new AddProductServlet(productService, securityService)), "/product/add");
//        context.addServlet(new ServletHolder(new EditProductServlet(productService, securityService)), "/product/edit/*");
//        context.addServlet(new ServletHolder(new LoginServlet(userService, securityService)), "/login");
//        context.addServlet(new ServletHolder(new DeleteProductServlet(productService, securityService)), "/product/delete/*");
//        context.addServlet(new ServletHolder(new LogoutServlet(securityService)), "/exit");
//        context.addServlet(new ServletHolder(new CartPageServlet(securityService)), "/cart");
//        context.addServlet(new ServletHolder(new AddToCartPageServlet(productService, securityService)), "/product/cart/*");
//
//        context.addFilter(new FilterHolder(new UserSecurityFilter(securityService)), "/cart", EnumSet.of(DispatcherType.REQUEST));
//        context.addFilter(new FilterHolder(new AdminSecurityFilter(securityService)), "/product/add/*", EnumSet.of(DispatcherType.REQUEST));
//        context.addFilter(new FilterHolder(new AdminSecurityFilter(securityService)), "/product/edit/*", EnumSet.of(DispatcherType.REQUEST));
//        context.addFilter(new FilterHolder(new AdminSecurityFilter(securityService)), "/product/delete/*", EnumSet.of(DispatcherType.REQUEST));
//
//        String portStr = System.getenv("PORT");
//        int port = portStr == null ? 8080 : Integer.valueOf(portStr);
//
//        Server server = new Server(port);
//        server.setHandler(context);
//
//        server.start();
//
//        LOG.info("Main: server started");
//    }
//}
