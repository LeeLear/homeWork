package com.gupaoedu.vip.mybatis.v2.executor;

public interface Executor {
    <T> T query(String statement, Object[] parameter, Class pojo);
}
