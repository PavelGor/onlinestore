package com.gordeev.onlinestore.locator;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

    private static final Map<Class,Object> SERVICES = new HashMap<>();

    public static void register(Class serviceClass, Object service){
        SERVICES.put(serviceClass, service);
    }

    public static <T> T getService(Class<T> clazz){
        return clazz.cast(SERVICES.get(clazz));
    }
}
