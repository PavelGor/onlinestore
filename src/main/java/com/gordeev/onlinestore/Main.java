package com.gordeev.onlinestore;

import com.gordeev.onlinestore.dao.jdbc.JdbcProductDao;
import com.gordeev.onlinestore.dao.jdbc.JdbcUserDao;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.web.filter.AdminSecurityFilter;
import com.gordeev.onlinestore.locator.ServiceLocator;
import com.gordeev.onlinestore.service.UserService;
import com.gordeev.onlinestore.web.filter.UserSecurityFilter;
import com.gordeev.onlinestore.web.servlet.*;
import com.gordeev.onlinestore.web.servlet.assets.AssetsServlet;
import com.gordeev.onlinestore.web.servlet.security.LogoutServlet;
import com.gordeev.onlinestore.web.servlet.security.LoginServlet;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import javax.sql.DataSource;
import java.util.EnumSet;

public class Main {
    public static void main(String[] args) throws Exception{
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost/onlinestore?useUnicode=true&characterEncoding=UTF8");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        ServiceLocator.register("dataSource", dataSource);

        ServiceLocator.register("productDao", new JdbcProductDao());
        ServiceLocator.register("userDao", new JdbcUserDao());
        ServiceLocator.register("userService", new UserService());
        ServiceLocator.register("securityService", new SecurityService());

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new ProductsServlet()), "/products");
        context.addServlet(new ServletHolder(new AddProductServlet()), "/product/add");
        context.addServlet(new ServletHolder(new EditProductServlet()), "/product/edit/*");
        context.addServlet(new ServletHolder(new LoginServlet()), "/login");
        context.addServlet(new ServletHolder(new DeleteProductServlet()), "/product/delete/*");
        context.addServlet(new ServletHolder(new LogoutServlet()), "/exit");
        context.addServlet(new ServletHolder(new CartPageServlet()), "/cart");
        context.addServlet(new ServletHolder(new AddToCartPageServlet()), "/product/cart/*");
        context.addServlet(new ServletHolder(new AssetsServlet()), "/assets/*");

        context.addFilter(UserSecurityFilter.class, "/cart", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(AdminSecurityFilter.class, "/product/add/*", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(AdminSecurityFilter.class, "/product/edit/*", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(AdminSecurityFilter.class, "/product/delete/*", EnumSet.of(DispatcherType.REQUEST));


        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}
