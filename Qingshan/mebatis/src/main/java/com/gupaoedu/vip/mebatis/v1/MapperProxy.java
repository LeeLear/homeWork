package com.gupaoedu.vip.mebatis.v1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/6 10:32
 * @description：
 * @modified By：
 * @version: $
 */
public class MapperProxy implements InvocationHandler {
    private SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String interfaceName = method.getDeclaringClass().getName();
        String methodName = method.getName();
        String statementId = interfaceName+"."+methodName;
        return sqlSession.selectOne(statementId,args[0]);
    }
}
