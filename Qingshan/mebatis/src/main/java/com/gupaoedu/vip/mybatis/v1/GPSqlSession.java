package com.gupaoedu.vip.mybatis.v1;

public class GPSqlSession {
    private com.gupaoedu.vip.mybatis.v1.GPConfiguration configuration;

    private com.gupaoedu.vip.mybatis.v1.GPExecutor executor;

    public GPSqlSession(com.gupaoedu.vip.mybatis.v1.GPConfiguration configuration, com.gupaoedu.vip.mybatis.v1.GPExecutor executor){
        this.configuration = configuration;
        this.executor = executor;
    }

    public <T> T selectOne(String statementId, Object paramater){
        // 根据statementId拿到SQL
        String sql = com.gupaoedu.vip.mybatis.v1.GPConfiguration.sqlMappings.getString(statementId);
        if(null != sql && !"".equals(sql)){
            return executor.query(sql, paramater );
        }
        return null;
    }

    public <T> T getMapper(Class clazz){
        return configuration.getMapper(clazz, this);
    }

}
