package com.gordeev.onlinestore;

import com.gordeev.onlinestore.dao.ProductDao;
import com.gordeev.onlinestore.dao.UserDao;
import com.gordeev.onlinestore.dao.jdbc.JdbcProductDao;
import com.gordeev.onlinestore.dao.jdbc.JdbcUserDao;
import com.gordeev.onlinestore.locator.ServiceLocator;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.service.ProductService;
import com.gordeev.onlinestore.service.UserService;
import com.gordeev.onlinestore.web.filter.AdminSecurityFilter;
import com.gordeev.onlinestore.web.filter.ContentTypeFilter;
import com.gordeev.onlinestore.web.filter.UserSecurityFilter;
import com.gordeev.onlinestore.web.servlet.*;
import com.gordeev.onlinestore.web.servlet.assets.AssetsServlet;
import com.gordeev.onlinestore.web.servlet.security.LoginServlet;
import com.gordeev.onlinestore.web.servlet.security.LogoutServlet;
import org.apache.commons.dbcp.BasicDataSource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Properties;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        BasicDataSource dataSource = new BasicDataSource();
        LOG.info("{}:{}", "JDBC_DATABASE_URL", dbUrl);

        if (dbUrl != null) {
            dbUrl += "&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
        } else {
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream("application.properties"));
            } catch (IOException e) {
                e.printStackTrace();
                LOG.info("bad file or properties in it");
            }
            dbUrl = properties.getProperty("url");
        }

        dataSource.setUrl(dbUrl);
        ServiceLocator.register(DataSource.class, dataSource);

        LOG.info("Main: got connection to database");

        ServiceLocator.register(ProductDao.class, new JdbcProductDao());
        ServiceLocator.register(UserDao.class, new JdbcUserDao());
        ServiceLocator.register(UserService.class, new UserService());
        ServiceLocator.register(ProductService.class, new ProductService());
        ServiceLocator.register(SecurityService.class, new SecurityService());
        LOG.info("Main: got Services");

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setBaseResource(Resource.newClassPathResource("/src/main/resources"));


        context.setBaseResource(Resource.newClassPathResource("/"));
        context.addServlet(new ServletHolder(new ProductsServlet()), "/");
        context.addServlet(new ServletHolder(new AddProductServlet()), "/product/add");
        context.addServlet(new ServletHolder(new EditProductServlet()), "/product/edit/*");
        context.addServlet(new ServletHolder(new LoginServlet()), "/login");
        context.addServlet(new ServletHolder(new DeleteProductServlet()), "/product/delete/*");
        context.addServlet(new ServletHolder(new LogoutServlet()), "/exit");
        context.addServlet(new ServletHolder(new CartPageServlet()), "/cart");
        context.addServlet(new ServletHolder(new AddToCartPageServlet()), "/product/cart/*");
        context.addServlet(new ServletHolder(new AssetsServlet()), "/assets/*");

        context.addFilter(ContentTypeFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(UserSecurityFilter.class, "/cart", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(AdminSecurityFilter.class, "/product/add/*", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(AdminSecurityFilter.class, "/product/edit/*", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(AdminSecurityFilter.class, "/product/delete/*", EnumSet.of(DispatcherType.REQUEST));

        String portStr = System.getenv("PORT");
        int port = portStr == null ? 8080 : Integer.valueOf(portStr);

        LOG.info("{}:{}", "PORT", port);

        Server server = new Server(port);
        server.setHandler(context);

        server.start();

        LOG.info("Main: server started");
    }
}
