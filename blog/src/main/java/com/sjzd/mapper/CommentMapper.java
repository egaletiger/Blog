package com.sjzd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sjzd.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-12-08
 */
public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> listComments(Map columns);

    @Insert("insert into blog_comment (id, blog_id, comment_id) values(default, #{blogId}, #{commentId})")
    Integer insertBlogComment(@Param("blogId") Integer blogId, @Param("commentId") int commentId);
}
