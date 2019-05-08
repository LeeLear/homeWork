package com.gupaoedu.vip.mebatis.v2.plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/6 14:40
 * @description：
 * @modified By：
 * @version: $
 */
public class Invocation {
    private Object target;
    private Method method;
    private Object[] args;

    public Invocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    public Object getTarget() {
        return target;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }

    public Object proceed() throws InvocationTargetException, IllegalAccessException {
        return method.invoke(target, args);
    }
}
