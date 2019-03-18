package com.gupaoedu.vip.pattern.singleton.register;

import java.util.concurrent.ConcurrentHashMap;

public class ContainerSingleton {
    public ContainerSingleton() {

    }
    private static ConcurrentHashMap<String,Object> ioc = new ConcurrentHashMap<>();
    //利用双重检查锁提高效率，在dubbo源码中也是经常见到
    public static Object getInstance(String name) {
        if(!ioc.contains(name)){
            synchronized (ioc){
                if (!ioc.contains(name)) {
                    try {
                        ioc.put(name, Class.forName(name).newInstance());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return ioc.get(name);

    }
}
