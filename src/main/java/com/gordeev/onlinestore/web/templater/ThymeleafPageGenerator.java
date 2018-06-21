package com.gordeev.onlinestore.web.templater;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;

public class ThymeleafPageGenerator {
    private TemplateEngine templateEngine;

    public TemplateEngine getTemplateEngine(ServletContext servletContext) {
        ServletContextTemplateResolver templateResolver =
                new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("webapp/templates/");
        templateResolver.setSuffix(".html");
//        templateResolver.setCacheTTLMs(3600000L);
        //templateResolver.setCharacterEncoding("utf-8");
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }
}