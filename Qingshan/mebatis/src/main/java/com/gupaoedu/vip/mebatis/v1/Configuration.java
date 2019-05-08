package com.gupaoedu.vip.mebatis.v1;

import java.lang.reflect.Proxy;
import java.util.ResourceBundle;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/6 10:31
 * @description：
 * @modified By：
 * @version: $
 */
public class Configuration {
    public static final ResourceBundle RESOURCE_BUNDLE;

    static {
        RESOURCE_BUNDLE =ResourceBundle.getBundle("v1/sql");
    }

    public <T> T getMapper(Class clazz, SqlSession sqlSession) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[]{clazz},new MapperProxy(sqlSession));
    }
}
