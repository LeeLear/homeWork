package com.gupaoedu.vip.mebatis.v2.generator.api;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/7 10:43
 * @description：
 * @modified By：
 * @version: $
 */
public interface ConnectionFactory {
    Connection getConnection() throws SQLException;
}
