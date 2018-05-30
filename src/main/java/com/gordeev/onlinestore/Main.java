package com.gordeev.onlinestore;

import com.gordeev.onlinestore.dao.jdbc.JdbcProductDao;
import com.gordeev.onlinestore.dao.jdbc.JdbcUserDao;
import com.gordeev.onlinestore.locator.ServiceLocator;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.service.UserService;
import com.gordeev.onlinestore.web.filter.AdminSecurityFilter;
import com.gordeev.onlinestore.web.filter.UserSecurityFilter;
import com.gordeev.onlinestore.web.servlet.*;
import com.gordeev.onlinestore.web.servlet.assets.AssetsServlet;
import com.gordeev.onlinestore.web.servlet.security.LoginServlet;
import com.gordeev.onlinestore.web.servlet.security.LogoutServlet;
import org.apache.commons.dbcp.BasicDataSource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class Main {
    static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        /*
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost/onlinestore?useUnicode=true&characterEncoding=UTF8");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        */

        // jdbc:postgresql://ec2-54-83-59-120.compute-1.amazonaws.com:5432/dq8c66cefkrh4?user=tbgmqcmkgywulg
        // &password=c13d443a4dc2547fb82fcf6b42926a3c2c549f87c24b8d657b0669a154ec7700&sslmode=require
        //password=c13d443a4dc2547fb82fcf6b42926a3c2c549f87c24b8d657b0669a154ec7700
        //user=tbgmqcmkgywulg
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        String serverName;
        LOG.info("{}:{}", "JDBC_DATABASE_URL", dbUrl);
        if (dbUrl != null) {
            dbUrl += "&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
        } else {
           // dbUrl = "jdbc:postgresql://ec2-54-83-59-120.compute-1.amazonaws.com:5432/dq8c66cefkrh4?user=tbgmqcmkgywulg" +
             //       "&password=c13d443a4dc2547fb82fcf6b42926a3c2c549f87c24b8d657b0669a154ec7700&sslmode=require&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory"
        }
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(dbUrl);
//        dataSource.setDataSourceName("dataSource");
//        dataSource.setServerName("ec2-54-83-59-120.compute-1.amazonaws.com");
//        dataSource.setDatabaseName("dq8c66cefkrh4");
//        dataSource.setUser("tbgmqcmkgywulg");
//        dataSource.setPassword("c13d443a4dc2547fb82fcf6b42926a3c2c549f87c24b8d657b0669a154ec7700");

        ServiceLocator.register("dataSource", dataSource);
        LOG.info("Main: got connection to database");

        ServiceLocator.register("productDao", new JdbcProductDao());
        ServiceLocator.register("userDao", new JdbcUserDao());
        ServiceLocator.register("userService", new UserService());
        ServiceLocator.register("securityService", new SecurityService());
        LOG.info("Main: got Services");

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


        String portStr = System.getenv("PORT");
        int port = portStr == null ? 8080 : Integer.valueOf(portStr);

        LOG.info("{}:{}", "PORT", port);

        Server server = new Server(port);
        server.setHandler(context);

        server.start();
        /*
        System.getProperties().forEach((o, o2) -> {
            LOG.info("{}      :{}", o, o2);
        });

        LOG.info("==================");
        System.getenv().forEach((o, o2) -> {
            LOG.info("{}:{}", o, o2);
        });
        LOG.info("==================");
*/


        LOG.info("Main: server started");

    }
}
