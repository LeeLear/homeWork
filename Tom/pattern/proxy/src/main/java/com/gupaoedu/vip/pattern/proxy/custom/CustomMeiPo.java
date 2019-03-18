package com.gupaoedu.vip.pattern.proxy.custom;

import com.gupaoedu.vip.pattern.proxy.entity.MeiPo;

import java.lang.reflect.Method;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/16 14:26
 * @description：
 * @modified By：
 * @version: $
 */
public class CustomMeiPo implements GPInvocationHandler{
    private MeiPo target;
    public Object getInstance(MeiPo target) throws Exception{
        this.target=target;
        Class<?> clazz = target.getClass();
        return GPProxy.newProxyInstance(new GPClassLoader(),clazz.getInterfaces(),this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("我是媒婆：我要给你找对象，现在已经拿到你的需求");
        System.out.println("开始物色");

        method.invoke(this.target,args);

        System.out.println("如果合适的话，就准备办事");

        return  null;

    }
}
