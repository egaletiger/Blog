package com.sjzd.mapper;

import com.sjzd.pojo.Tag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *  操作表blog_tag
 */
public interface BlogTagMapper {
    Integer insertBlogTagByBlogId(@Param("blogId")int blogId, @Param("tagIds") List<Integer> tagIds);

    Integer deleteBlogTagByBlogId(@Param("blogId")int blogId, @Param("old") List<Integer> old);

    @Delete("delete form blog_tag where tag_id = #{tagId}")
    Integer deleteBlogTagByTagId(Integer tagId);

    @Select("select count(*) from blog_tag where tag_id = #{tagId}")
    Integer countBlogByTagId(Integer tagId);

    @Select("select t.id, t.name, count(*) blogSize from tag t " +
            " right join blog_tag bt on t.id = bt.tag_id group by t.id order by blogSize desc limit 0,${size}")
    List<Tag> listTop(long size);
}
