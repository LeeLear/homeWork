package com.gupaoedu.vip.mebatis.v1;

import com.gupaoedu.vip.mapper.BlogMapper;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/6 11:01
 * @description：
 * @modified By：
 * @version: $
 */
public class Boot {
    public static void main(String[] args) {
        SqlSession sqlSession = new SqlSession(new Configuration(),new Executor());
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        blogMapper.selectBlogById(1);
    }

}
