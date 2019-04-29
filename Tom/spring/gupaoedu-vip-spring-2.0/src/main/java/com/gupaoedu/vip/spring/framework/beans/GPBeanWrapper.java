package com.gupaoedu.vip.spring.framework.beans;

import java.lang.reflect.Method;

/**
 * Created by Tom.
 */
public class GPBeanWrapper {

    private Object wrappedInstance;
    private Class<?> wrappedClass;

    public GPBeanWrapper(Object wrappedInstance){
        this.wrappedInstance = wrappedInstance;
    }

    public Object getWrappedInstance(){
        return this.wrappedInstance;
    }

    // 返回代理以后的Class
    // 可能会是这个 $Proxy0
    public Class<?> getWrappedClass(){
        return this.wrappedInstance.getClass();
    }

    public static void main(String[] args) {
        Method[] methods = GPBeanWrapper.class.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.toGenericString());
        }
    }
}
