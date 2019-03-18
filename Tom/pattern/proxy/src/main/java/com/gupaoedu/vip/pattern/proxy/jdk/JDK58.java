package com.gupaoedu.vip.pattern.proxy.jdk;

import com.gupaoedu.vip.pattern.proxy.entity.Person;
import com.gupaoedu.vip.pattern.proxy.entity.Worker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/15 23:14
 * @description：
 * @modified By：
 * @version: $
 */
public class JDK58 implements InvocationHandler {
    private Person target;

    public Worker wrapPerson(Person person){
        this.target =person;
        Class clazz=person.getClass();
        return (Worker) Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("帮你找工作，要求给我");
        method.invoke(this.target,args);
        System.out.println("经过一番周折，找到了");
        return null;
    }
}
