package com.gupaoedu.vip.pattern.proxy.custom;

import java.lang.reflect.Method;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/16 13:12
 * @description：
 * @modified By：
 * @version: $
 */
public interface GPInvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
