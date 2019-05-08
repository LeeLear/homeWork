package com.gupaoedu.vip.mebatis.v1;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/6 10:32
 * @description：
 * @modified By：
 * @version: $
 */
public class SqlSession {
    private Configuration configuration;
    private Executor executor;

    public SqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    public <T> T getMapper(Class clazz){
        return configuration.getMapper(clazz,this);
    }

    public Object selectOne(String statementId, Object arg) {
        String sql = Configuration.RESOURCE_BUNDLE.getString(statementId);
        if(null!=sql&&!"".equals(sql)){
            return  executor.query(sql,arg);
        }
        return null;
    }
}
