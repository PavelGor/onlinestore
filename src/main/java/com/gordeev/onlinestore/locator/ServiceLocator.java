package com.gordeev.onlinestore.locator;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

    private static final Map<String,Object> SERVICES = new HashMap<>();

    public static void register(String name, Object service){
        SERVICES.put(name, service);
    }

    public static Object getService(String serviceName){
        return SERVICES.get(serviceName);
    }
}
