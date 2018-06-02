package com.gordeev.onlinestore.web.templater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PageGenerator {
    private static PageGenerator pageGenerator;
    private final Configuration cfg;

    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            cfg.setClassForTemplateLoading(getClass(), "/webapp/templates");
            Template template = cfg.getTemplate(filename);
            template.process(data, stream);
        } catch (IOException | TemplateException | NullPointerException e) {
            throw new RuntimeException(e);
        }
        return stream.toString();
    }

    public String getPage(String filename) {
        return getPage(filename, new HashMap<>());
    }

    private PageGenerator() {
        cfg = new Configuration();
    }
}
