package com.gordeev.onlinestore.web.templater;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;

public class ThymeleafAppUtil {
    private static TemplateEngine templateEngine;
    static {
        ServletContextHandler contextHandler= new ServletContextHandler();
        contextHandler.getServletContext();
        ServletContextTemplateResolver templateResolver =
                new ServletContextTemplateResolver(contextHandler.getServletContext());
        templateResolver.setTemplateMode(TemplateMode.HTML5);
        templateResolver.setPrefix("/webapp/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheTTLMs(3600000L);
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }
    public static TemplateEngine getTemplateEngine() {
        return templateEngine;
    }
}