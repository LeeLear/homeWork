package com.gupaoedu.vip.mebatis.v2.executor;

import com.gupaoedu.vip.mebatis.v2.executor.resultSet.DefaultResultSetHandler;
import com.gupaoedu.vip.mebatis.v2.executor.statement.BaseStatementHandler;
import com.gupaoedu.vip.mebatis.v2.executor.statement.StatementHandler;
import com.gupaoedu.vip.mebatis.v2.mapping.MappedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/6 13:51
 * @description：
 * @modified By：
 * @version: $
 */
public class SimpleExecutor extends BaseExecutor{


    @Override
    protected <E> List<E> doQuery(MappedStatement ms, Object parameter) throws SQLException {

        StatementHandler statementHandler = new BaseStatementHandler(this,new DefaultResultSetHandler(ms,this), parameter, ms);
        PreparedStatement statement = prepareStatement(statementHandler,ms);
        return statementHandler.query(statement);
    }

    private PreparedStatement prepareStatement(StatementHandler statementHandler, MappedStatement ms) throws SQLException {
        Connection conn = getConnection();
        return conn.prepareStatement(ms.getSql());
    }

    @Override
    public int doUpdate(MappedStatement ms, Object parameter) throws SQLException {
        Statement stmt = null;
        try {
            StatementHandler handler = new BaseStatementHandler(this,new DefaultResultSetHandler(ms,this), parameter, ms);
            stmt = prepareStatement(handler,ms);
            return handler.update(stmt);
        } finally {
            closeStatement(stmt);
        }
    }

    protected void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }
}
