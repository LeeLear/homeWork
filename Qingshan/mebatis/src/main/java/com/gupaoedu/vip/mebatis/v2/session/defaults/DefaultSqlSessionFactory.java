package com.gupaoedu.vip.mebatis.v2.session.defaults;

import com.gupaoedu.vip.mebatis.v2.session.Configuration;
import com.gupaoedu.vip.mebatis.v2.session.SqlSession;
import com.gupaoedu.vip.mebatis.v2.session.SqlSessionFactory;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/6 13:34
 * @description：
 * @modified By：
 * @version: $
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private final Configuration configuration;

    public DefaultSqlSessionFactory() {
        configuration = new Configuration();
    }

    @Override
    public SqlSession openSession() {

        return new DefaultSqlSession(configuration);
    }
}
