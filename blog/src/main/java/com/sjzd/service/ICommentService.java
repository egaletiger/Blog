package com.sjzd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sjzd.pojo.Comment;

import java.util.List;

/**
 * <p>
 *  评论服务类
 * </p>
 *
 * @author 
 * @since 2019-12-08
 */
public interface ICommentService extends IService<Comment> {

    List<Comment> listComments(Integer blogId, Integer parentCommentId);

    Integer saveComment(Comment comment);
}
