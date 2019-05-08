package com.gupaoedu.vip.mybatis.v1;

import com.gupaoedu.vip.mapper.BlogMapper;

public class MyBatisBoot {
    public static void main(String[] args) {
        com.gupaoedu.vip.mybatis.v1.GPSqlSession sqlSession = new com.gupaoedu.vip.mybatis.v1.GPSqlSession(new com.gupaoedu.vip.mybatis.v1.GPConfiguration(), new com.gupaoedu.vip.mybatis.v1.GPExecutor());
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        blogMapper.selectBlogById(1);
    }
}
