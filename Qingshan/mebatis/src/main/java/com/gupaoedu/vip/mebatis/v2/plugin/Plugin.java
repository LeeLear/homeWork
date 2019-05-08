package com.gupaoedu.vip.mebatis.v2.plugin;

import com.gupaoedu.vip.mebatis.v2.annotation.Intercepts;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/6 14:38
 * @description：
 * @modified By：
 * @version: $
 */
public class Plugin implements InvocationHandler {
    private Object target;
    private Interceptor interceptor;

    /**
     *
     * @param target 被代理对象
     * @param interceptor 拦截器（插件）
     */
    public Plugin(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    /**
     * 对被代理对象进行代理，返回代理类
     * @param obj
     * @param interceptor
     * @return
     */
    public static Object wrap(Object obj, Interceptor interceptor) {
        Class clazz = obj.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new Plugin(obj, interceptor));
    }

    /**
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 自定义的插件上有@Intercepts注解，指定了拦截的方法
        if (interceptor.getClass().isAnnotationPresent(Intercepts.class)) {
            // 如果是被拦截的方法，则进入自定义拦截器的逻辑
            if (method.getName().equals(interceptor.getClass().getAnnotation(Intercepts.class).value())) {
                return interceptor.intercept(new Invocation(target, method, args));
            }
        }
        // 非被拦截方法，执行原逻辑
        return method.invoke(target, args);
    }
}
