package com.gupaoedu.vip.mebatis.v2.binding;

import com.gupaoedu.vip.mebatis.v2.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/6 13:43
 * @description：
 * @modified By：
 * @version: $
 */
public class MapperRegistry {

    private final Map<Class<?>, MapperProxyFactory> knownMappers = new HashMap<>();

    public <T> void addMapper(Class<T> clazz){
        knownMappers.put(clazz, new MapperProxyFactory(clazz));
    }

    /**
     * 创建一个代理对象
     */
    public <T> T getMapper(Class<T> clazz, SqlSession sqlSession) {
        MapperProxyFactory proxyFactory = knownMappers.get(clazz);
        if (proxyFactory == null) {
            throw new RuntimeException("Type: " + clazz + " can not find");
        }
        return (T)proxyFactory.newInstance(sqlSession);
    }
}
