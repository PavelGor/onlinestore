package com.gordeev.onlinestore.web.templater;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;

public class ThymeleafPageGenerator {
    public TemplateEngine getTemplateEngine(ServletContext servletContext) {
        ClassLoaderTemplateResolver templateResolver =
                new ClassLoaderTemplateResolver();


        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/webapp/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheTTLMs(3600000L);
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }
}