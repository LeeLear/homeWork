package com.gupaoedu.vip.mebatis.v2.binding;

import com.gupaoedu.vip.mebatis.v2.session.SqlSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/6 13:42
 * @description：
 * @modified By：
 * @version: $
 */
public class MapperProxy<T> implements InvocationHandler, Serializable {

    private final SqlSession sqlSession;
    private final Class<T> mapperInterface;
    private final Map<Method, MapperMethod> methodCache;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface, Map<Method, MapperMethod> methodCache) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String mapperInterface = method.getDeclaringClass().getName();
        String methodName = method.getName();
        String statementId = mapperInterface + "." + methodName;
        // 如果根据接口类型+方法名能找到映射的SQL，则执行SQL
        if (sqlSession.getConfiguration().hasStatement(statementId)) {
            final MapperMethod mapperMethod = cachedMapperMethod(method);
            return mapperMethod.execute(sqlSession, args);
        }
        // 否则直接执行被代理对象的原方法
        return method.invoke(proxy, args);
    }

    private MapperMethod cachedMapperMethod(Method method) {
        return methodCache.computeIfAbsent(method, k -> new MapperMethod(mapperInterface, method, sqlSession.getConfiguration()));
    }
}
