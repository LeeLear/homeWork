package com.gupaoedu.vip.mebatis.v2.executor.statement;

import com.gupaoedu.vip.mebatis.v2.executor.Executor;
import com.gupaoedu.vip.mebatis.v2.executor.parameter.ParameterHandler;
import com.gupaoedu.vip.mebatis.v2.executor.resultSet.ResultSetHandler;
import com.gupaoedu.vip.mebatis.v2.mapping.MappedStatement;
import com.gupaoedu.vip.mebatis.v2.scripting.defaults.DefaultParameterHandler;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/6 14:00
 * @description：
 * @modified By：
 * @version: $
 */
public class BaseStatementHandler implements StatementHandler{

    protected final Executor executor;

    private final ResultSetHandler resultSetHandler;

    private final Object parameterObject;

    protected final MappedStatement mappedStatement;

    public BaseStatementHandler(Executor executor, ResultSetHandler resultSetHandler, Object parameterObject, MappedStatement mappedStatement) {
        this.executor = executor;
        this.resultSetHandler = resultSetHandler;
        this.parameterObject = parameterObject;
        this.mappedStatement = mappedStatement;
    }

    @Override
    public <E> List<E> query(Statement statement) throws SQLException {

        PreparedStatement preparedStatement =(PreparedStatement) statement;
        ParameterHandler parameterHandler = new DefaultParameterHandler(preparedStatement);
        parameterHandler.setParameters(parameterObject,null);

        List result = null;
        try {
            preparedStatement.execute();
            result = resultSetHandler.handle(preparedStatement.getResultSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public <T> T query(String statement, Object[] parameter, Class pojo){
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Object result = null;

        try {
            conn = executor.getConnection();
            preparedStatement = conn.prepareStatement(statement);
            ParameterHandler parameterHandler = new DefaultParameterHandler(preparedStatement);
            parameterHandler.setParameters(parameter,null);
            preparedStatement.execute();
            try {
                result = resultSetHandler.handle(preparedStatement.getResultSet(), pojo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return (T)result;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                conn = null;
            }
        }
        // 只在try里面return会报错
        return null;
    }

    @Override
    public int update(Statement statement) throws SQLException {
        PreparedStatement preparedStatement =(PreparedStatement) statement;
        ParameterHandler parameterHandler = new DefaultParameterHandler(preparedStatement);
        Map<Integer,String> properties= mappedStatement.getKeyProperties();
        parameterHandler.setParameters(parameterObject,properties);
        int result = 0;
        try {
            preparedStatement.execute();
            result = preparedStatement.getUpdateCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
