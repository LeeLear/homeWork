package com.gupaoedu.vip.mebatis.v2.executor;

import com.gupaoedu.vip.mebatis.v2.cache.CacheKey;
import com.gupaoedu.vip.mebatis.v2.mapping.MappedStatement;
import sun.plugin2.main.server.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/6 13:49
 * @description：
 * @modified By：
 * @version: $
 */
public interface Executor {
    <T> T query(String statement, Object[] parameter, Class pojo);

    <E> List<E> query(MappedStatement ms, Object parameter, CacheKey cacheKey) throws SQLException;

    <E> List<E> query(MappedStatement ms, Object parameter) throws SQLException;

    Connection getConnection();


    int update(MappedStatement ms, Object parameterObject) throws SQLException;
}
