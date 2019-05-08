package com.gupaoedu.vip.mapper;

import com.gupaoedu.vip.mybatis.v2.annotation.Entity;
import com.gupaoedu.vip.mybatis.v2.annotation.Select;

import java.util.List;

@Entity(Blog.class)
public interface BlogMapper {
    /**
     * 根据主键查询文章
     * @param bid
     * @return
     */
    @Select("select * from blog where bid = %d")
    public Blog selectBlogById(Integer bid);

    public List<Blog> selectBlog();

    public int insertBlog(Blog blog);

    public int updateByPrimaryKey(Blog blog);

}
