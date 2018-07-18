package com.gordeev.onlinestore.context;

import com.gordeev.applicationcontextlibrary.ApplicationContext;
import com.gordeev.applicationcontextlibrary.ClassPathApplicationContext;

public class Context {

    private static final ApplicationContext applicationContext = new ClassPathApplicationContext("src/main/resources/context.xml");

    public static <T> T getContext(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }
}
