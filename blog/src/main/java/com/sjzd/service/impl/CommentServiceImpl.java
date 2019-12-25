package com.sjzd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjzd.exception.NotFoundException;
import com.sjzd.mapper.CommentMapper;
import com.sjzd.pojo.Comment;
import com.sjzd.service.ICommentService;
import com.sjzd.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author 
 * @since 2019-12-08
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Transactional
    @Override
    public Integer saveComment(Comment comment) {
        // 设置创建日期
        String nowTimeString = TimeUtils.getNowTimeString();
        comment.setCreateTime(nowTimeString);
        int flag = commentMapper.insert(comment);
        if (flag > 0) {
            // 同步插入blog_comment
            QueryWrapper<Comment> queryWrapper = new QueryWrapper();
            queryWrapper.eq("create_time", nowTimeString)
                    .eq("email", comment.getEmail());
            Comment temp = commentMapper.selectOne(queryWrapper);
            if (null == temp) {
                throw new NotFoundException("网络繁忙请重试");
            }
            commentMapper.insertBlogComment(comment.getBlogId(), temp.getId());
        }
        return flag;
    }

    @Override
    public List<Comment> listComments(Integer blogId, Integer parentCommentId) {
        Map<String, Integer> map = new HashMap<>();
        map.put("blogId", blogId);
        map.put("pid", parentCommentId);
        List<Comment> comments = commentMapper.listComments(map);
        // 整合所有围绕该评论展开的评论
        combineReplyComments(comments);
        return comments;
    }

    /**
     *  在页面中评论内容只分为两级，即第一评论和围绕第一评论展开的评论
     *  该方法就是将所有围绕第一评论展开的评论的子评论整合在一起
     *  （也就是将第三级评论甚至第四级等等，都整合到第二级评论中）
     * @param comments
     * @return
     */
    private static void combineReplyComments(List<Comment> comments) {
        for (final Comment firstComment : comments){
            List<Comment> secondComment = firstComment.getReplyComments();
            if (null != secondComment) {
                firstComment.setReplyComments(recursiveCombine(secondComment));
            }
        }
    }

    /**
     *  递归遍历将所有一级评论以后的评论，都整合到第二级评论中
     * @param comments
     */
    private static List<Comment> recursiveCombine(List<Comment> comments) {
        if (null == comments) {
            return null;
        }
        List<Comment> combine = new ArrayList<>(10);
        for (final Comment comment : comments) {
            combine.add(comment);
            List<Comment> child = recursiveCombine(comment.getReplyComments());
            if (null != child) {
                combine.addAll(child);
            }
        }
        return combine;
    }

    // ----for test----
    public static void main(String[] args) {
        List<Comment> firsts = new ArrayList<>(4);
        for (int i = 1; i <5; i++) {
            Comment first = new Comment();
            first.setContent(i + "楼");
            firsts.add(first);
            List<Comment> seconds = new ArrayList<>(2);
            for (int j = 1; j < 3; j++) {
                Comment second = new Comment();
                second.setContent(i + "-" + j + "楼");
                seconds.add(second);
                List<Comment> thirds = new ArrayList<>(2);
                for (int k = 1; k < 3; k++) {
                    Comment third = new Comment();
                    third.setContent(i + "-" + j + "-" + k +  "楼");
                    seconds.add(third);
                }
                second.setReplyComments(thirds);
            }
            first.setReplyComments(seconds);
        }

        combineReplyComments(firsts);
        for (final Comment first : firsts) {
            System.out.println(first.getContent());
            for (final Comment third : first.getReplyComments()) {
                System.out.println("----" + third.getContent());
            }
        }
    }
}
