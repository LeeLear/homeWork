package com.gupaoedu.vip.mebatis.v2.executor;

import com.gupaoedu.vip.mebatis.v2.cache.CacheKey;
import com.gupaoedu.vip.mebatis.v2.mapping.MappedStatement;
import sun.plugin2.main.server.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/6 13:50
 * @description：
 * @modified By：
 * @version: $
 */
public class CachingExecutor implements Executor{
    private Executor delegate;
    private static final Map<Integer, Object> cache = new HashMap<>();

    public CachingExecutor(Executor delegate) {
        this.delegate = delegate;
    }

    @Override
    public <T> T query(String statement, Object[] parameter, Class pojo)  {
        // 计算CacheKey
        CacheKey cacheKey = new CacheKey();
        cacheKey.update(statement);
        cacheKey.update(joinStr(parameter));
        // 是否拿到缓存
        if (cache.containsKey(cacheKey.getCode())) {
            // 命中缓存
            System.out.println("【命中缓存】");
            return (T)cache.get(cacheKey.getCode());
        }else{
            // 没有的话调用被装饰的SimpleExecutor从数据库查询
            Object obj = delegate.query(statement, parameter, pojo);
            cache.put(cacheKey.getCode(), obj);
            return (T)obj;
        }
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, CacheKey cacheKey) throws SQLException {
        // 计算CacheKey
        cacheKey = new CacheKey();
        cacheKey.update(ms.getId());
        cacheKey.update(joinStr(parameter));
        // 是否拿到缓存
        if (cache.containsKey(cacheKey.getCode())) {
            // 命中缓存
            System.out.println("【命中缓存】");
            return (List<E>) cache.get(cacheKey.getCode());
        }else{
            // 没有的话调用被装饰的SimpleExecutor从数据库查询
            Object obj = delegate.query(ms, parameter);
            cache.put(cacheKey.getCode(), obj);
            return (List<E>) obj;
        }
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter) throws SQLException {
        // 计算CacheKey
        CacheKey cacheKey = new CacheKey();
        cacheKey.update(ms.getId());
        cacheKey.update(joinStr(parameter));
        // 是否拿到缓存
        if (cache.containsKey(cacheKey.getCode())) {
            // 命中缓存
            System.out.println("【命中缓存】");
            return (List<E>) cache.get(cacheKey.getCode());
        }else{
            // 没有的话调用被装饰的SimpleExecutor从数据库查询
            Object obj = delegate.query(ms, parameter);
            cache.put(cacheKey.getCode(), obj);
            return (List<E>) obj;
        }
    }

    @Override
    public Connection getConnection() {
        return delegate.getConnection();
    }



    // 为了命中缓存，把Object[]转换成逗号拼接的字符串，因为对象的HashCode都不一样
    public String joinStr(Object...objs){
        StringBuffer sb = new StringBuffer();

        if (objs[0] instanceof List){
            for (Object o : (List) objs[0]) {
                sb.append(o.toString()+",");
            }
        }else {

            if (objs != null && objs.length > 0) {
                for (Object objStr : objs) {
                    sb.append(objStr.toString() + ",");
                }
            }
        }
        int len = sb.length();
        if(len >0){
            sb.deleteCharAt(len-1);
        }

        return  sb.toString();
    }

    @Override
    public int update(MappedStatement ms, Object parameterObject) throws SQLException {
        clearLocalCache();
        return delegate.update(ms, parameterObject);
    }

    private void clearLocalCache() {
        System.out.println("缓存失效");
        cache.clear();
    }
}
