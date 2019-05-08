package com.gupaoedu.vip.mebatis.v2.session;

import java.sql.Connection;
import java.util.List;

public interface SqlSession {
    <T> T getMapper(Class<T> type);
    public Configuration getConfiguration();

    <T> T selectOne(String statement, Object[] parameter, Class pojo);

    <T> T selectOne(String statement, Object parameter);

    <E> List<E> selectList(String statement, Object parameter);

    int insert(String statement);

    int insert(String statement, Object parameter);

    /**
     * Retrieves inner database connection.
     * @return Connection
     */
    Connection getConnection();

    int update(String statement, Object parameter);
}
