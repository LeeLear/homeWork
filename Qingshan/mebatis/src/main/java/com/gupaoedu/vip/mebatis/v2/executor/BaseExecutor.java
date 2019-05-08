package com.gupaoedu.vip.mebatis.v2.executor;

import com.gupaoedu.vip.mebatis.v2.cache.CacheKey;
import com.gupaoedu.vip.mebatis.v2.generator.api.ConnectionFactory;
import com.gupaoedu.vip.mebatis.v2.generator.internal.JDBCConnectionFactory;
import com.gupaoedu.vip.mebatis.v2.mapping.MappedStatement;
import sun.plugin2.main.server.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/7 12:31
 * @description：
 * @modified By：
 * @version: $
 */
public abstract class BaseExecutor implements Executor{



    @Override
    public Connection getConnection() {
        ConnectionFactory connectionFactory = new JDBCConnectionFactory();
        try {
            return connectionFactory.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T query(String statement, Object[] parameter, Class pojo) {
        return null;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, CacheKey cacheKey) throws SQLException {
        return null;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter) throws SQLException {
        return doQuery( ms,  parameter);
    }

    protected abstract <E> List<E> doQuery(MappedStatement ms, Object parameter)
            throws SQLException;

    @Override
    public int update(MappedStatement ms, Object parameter) throws SQLException {

        return doUpdate(ms, parameter);
    }

    public abstract int doUpdate(MappedStatement ms, Object parameter) throws SQLException;
}
