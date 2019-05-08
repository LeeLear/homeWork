package com.gupaoedu.vip.mebatis.v2.executor.statement;

import sun.plugin2.main.server.ResultHandler;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/6 13:54
 * @description：
 * @modified By：
 * @version: $
 */
public interface StatementHandler {
    <E> List<E> query(Statement statement)
            throws SQLException;
    public <T> T query(String statement, Object[] parameter, Class pojo);

    int update(Statement statement)
            throws SQLException;
}
