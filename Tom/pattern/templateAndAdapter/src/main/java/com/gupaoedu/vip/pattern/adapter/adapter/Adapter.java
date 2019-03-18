package com.gupaoedu.vip.pattern.adapter.adapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EmptyStackException;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/17 10:30
 * @description：
 * @modified By：
 * @version: $
 */
public class Adapter {

    public static Object doAdapter(String str){
        String[] result =AdapterRule.doBreakDown(str);
        String className =result[0];
        String methodName = result[1];
        Class<?>[] parameterTypes=new Class[result.length/2-1];
        Object[] parameters=new Object[result.length/2-1];


        try {
            for(int i=2;i<result.length;i=i+2){
                parameterTypes[i/2-1]=Class.forName(result[i]);
                //这里简单演示，最好还是用json或者xml比较好
                /*Constructor c=Class.forName(result[i]).getConstructor();
                c.setAccessible(true);*/
                if (result[i].equals("java.lang.Integer")) {
                    parameters[i / 2 - 1] = Integer.parseInt(result[i + 1].trim());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        Object obj=null;

        try {

            Class clazz= Class.forName(className);
            Method method = clazz.getMethod(methodName,parameterTypes);
            obj=method.invoke(clazz.newInstance(),parameters);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
