package com.gordeev.onlinestore;

import com.gordeev.onlinestore.dao.jdbc.JdbcProductDao;
import com.gordeev.onlinestore.locator.ServiceLocator;
import com.gordeev.onlinestore.web.servlet.AddProductServlet;
import com.gordeev.onlinestore.web.servlet.ProductsServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception{
        ServiceLocator.register("productDao", new JdbcProductDao());

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new ProductsServlet()), "/products");
        context.addServlet(new ServletHolder(new AddProductServlet()), "/product/add");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}
