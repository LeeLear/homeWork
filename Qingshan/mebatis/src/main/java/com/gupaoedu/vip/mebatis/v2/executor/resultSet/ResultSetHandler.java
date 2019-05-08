package com.gupaoedu.vip.mebatis.v2.executor.resultSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface ResultSetHandler {
    public <T> T handle(ResultSet resultSet, Class type);
    public <T> List handle(ResultSet resultSet);
    <E> List<E> handleResultSets(Statement stmt) throws SQLException;
}
