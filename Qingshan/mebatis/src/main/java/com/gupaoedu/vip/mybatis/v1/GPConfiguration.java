package com.gupaoedu.vip.mybatis.v1;

import java.lang.reflect.Proxy;
import java.util.ResourceBundle;

public class GPConfiguration {
    public static final ResourceBundle sqlMappings;

    static{
        sqlMappings = ResourceBundle.getBundle("v1/sql");
    }

    public <T> T getMapper(Class clazz, GPSqlSession sqlSession) {
        return (T)Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{clazz},
                new GPMapperProxy(sqlSession));
    }


}
