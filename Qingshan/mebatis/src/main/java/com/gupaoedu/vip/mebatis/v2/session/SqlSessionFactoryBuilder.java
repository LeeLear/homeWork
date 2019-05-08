package com.gupaoedu.vip.mebatis.v2.session;

import com.gupaoedu.vip.mebatis.v2.session.defaults.DefaultSqlSessionFactory;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/6 14:20
 * @description：
 * @modified By：
 * @version: $
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory builder(){
        return new DefaultSqlSessionFactory();
    }
}
