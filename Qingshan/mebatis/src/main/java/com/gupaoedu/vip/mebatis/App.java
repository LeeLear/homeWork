package com.gupaoedu.vip.mebatis;

import com.gupaoedu.vip.mapper.Blog;
import com.gupaoedu.vip.mapper.BlogMapper;
import com.gupaoedu.vip.mebatis.v2.session.SqlSession;
import com.gupaoedu.vip.mebatis.v2.session.SqlSessionFactory;
import com.gupaoedu.vip.mebatis.v2.session.SqlSessionFactoryBuilder;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().builder();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
//        Blog blog = new Blog();
//        blog.setBid(3);
//        blog.setName("lie");
//        blog.setAuthorId(3);
//        int n =blogMapper.insertBlog(blog);
//        System.out.println(n);
//        Blog blog = blogMapper.selectBlogById(1);
//        System.out.println("第一次查询: " + blog);
//        System.out.println();
//        blog = blogMapper.selectBlogById(1);
//        System.out.println("第二次查询: " + blog);

        List<Blog> list = blogMapper.selectBlog();
        System.out.println(list);

        Blog blog = new Blog();
        blog.setBid(3);
        blog.setName("lear");
        blog.setAuthorId(4);
        blogMapper.updateByPrimaryKey(blog);
        list = blogMapper.selectBlog();
        System.out.println(list);
    }
}
