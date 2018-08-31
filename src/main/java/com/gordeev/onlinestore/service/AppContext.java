package com.gordeev.onlinestore.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppContext {
    private final static ApplicationContext INSTANCE = new ClassPathXmlApplicationContext("context.xml");

    public static ApplicationContext getInstance() {
        return INSTANCE;
    }
}
