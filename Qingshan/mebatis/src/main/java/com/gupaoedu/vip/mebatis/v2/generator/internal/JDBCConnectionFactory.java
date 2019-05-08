package com.gupaoedu.vip.mebatis.v2.generator.internal;

import com.gupaoedu.vip.mebatis.v2.generator.api.ConnectionFactory;
import com.gupaoedu.vip.mebatis.v2.session.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/7 10:44
 * @description：
 * @modified By：
 * @version: $
 */
public class JDBCConnectionFactory implements ConnectionFactory {
    @Override
    public Connection getConnection() throws SQLException {
        String driver = Configuration.dataSource.getString("jdbc.driver");
        String url =  Configuration.dataSource.getString("jdbc.url");
        String username = Configuration.dataSource.getString("jdbc.username");
        String password = Configuration.dataSource.getString("jdbc.password");
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
