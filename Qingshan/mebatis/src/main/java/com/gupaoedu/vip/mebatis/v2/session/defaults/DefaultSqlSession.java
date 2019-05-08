package com.gupaoedu.vip.mebatis.v2.session.defaults;


import com.gupaoedu.vip.mebatis.v2.executor.Executor;
import com.gupaoedu.vip.mebatis.v2.mapping.MappedStatement;
import com.gupaoedu.vip.mebatis.v2.session.Configuration;
import com.gupaoedu.vip.mebatis.v2.session.SqlSession;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/6 13:32
 * @description：
 * @modified By：
 * @version: $
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration= configuration;
        this.executor = configuration.newExecutor();
    }
    @Override
    public Configuration getConfiguration() {
        return configuration;
    }


    @Override
    public <T> T getMapper(Class<T> clazz){
        return configuration.getMapper(clazz, this);
    }

    @Override
    public <T> T selectOne(String statement, Object[] parameter, Class pojo)  {
        String sql = getConfiguration().getMappedStatement(statement).getSql();
        // 打印代理对象时会自动调用toString()方法，触发invoke()
        return executor.query(sql, parameter, pojo);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        List<T> list = this.selectList(statement, parameter);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new RuntimeException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        } else {
            return null;
        }
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        MappedStatement ms = configuration.getMappedStatement(statement);
        try {
            return executor.query(ms, parameter,null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int insert(String statement) {
        return update(statement, null);
    }

    @Override
    public int insert(String statement, Object parameter) {
        return update(statement, parameter);
    }

    @Override
    public Connection getConnection() {
        return executor.getConnection();
    }


    @Override
    public int update(String statement, Object parameter) {
        try {
            //dirty = true;
            MappedStatement ms = configuration.getMappedStatement(statement);
            return executor.update(ms, parameter);
        } catch (Exception e) {
            throw new RuntimeException("Error updating database.  Cause: " + e, e);
        } finally {

        }
    }

}
