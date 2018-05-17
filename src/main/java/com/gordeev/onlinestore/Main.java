package com.gordeev.onlinestore;

import com.gordeev.onlinestore.dao.jdbc.JdbcProductDao;
import com.gordeev.onlinestore.filter.SecurityFilter;
import com.gordeev.onlinestore.locator.ServiceLocator;
import com.gordeev.onlinestore.service.UserService;
import com.gordeev.onlinestore.web.servlet.AddProductServlet;
import com.gordeev.onlinestore.web.servlet.EditProductServlet;
import com.gordeev.onlinestore.web.servlet.LoginPageServlet;
import com.gordeev.onlinestore.web.servlet.ProductsServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class Main {
    public static void main(String[] args) throws Exception{
        ServiceLocator.register("productDao", new JdbcProductDao());
        ServiceLocator.register("userService", new UserService());

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new ProductsServlet()), "/products");
        context.addServlet(new ServletHolder(new AddProductServlet()), "/product/add");
        context.addServlet(new ServletHolder(new EditProductServlet()), "/product/edit/*");
        context.addServlet(new ServletHolder(new LoginPageServlet()), "/login");

        context.addFilter(SecurityFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}
