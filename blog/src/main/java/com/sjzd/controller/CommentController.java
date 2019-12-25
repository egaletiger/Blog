package com.sjzd.controller;

import com.sjzd.pojo.Comment;
import com.sjzd.pojo.User;
import com.sjzd.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *  评论页面请求处理
 */
@Controller
public class CommentController {

    @Autowired
    private ICommentService commentService;

    /**
     *  根据博客id查找对应评论
     * @param blogId
     * @param model
     * @return
     */
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Integer blogId, Model model) {
        List<Comment> comments = commentService.listComments(blogId, -1);
        model.addAttribute("comments", comments);
        return "blog :: commentList";
    }

    /**
     *  添加博客评论
     * @param comment
     * @param session
     * @return
     */
    @PostMapping("/comments/add")
    public String commentAdd(Comment comment, HttpSession session) {
        User user = (User)session.getAttribute("user");
        if (null != user) {
            comment.setIsAdminComment(true);
            comment.setAvatar(user.getAvatar());
        } else {
            comment.setAvatar("/img/default.jpg");
        }
         commentService.saveComment(comment);
        return "redirect:/comments/" + comment.getBlogId();
    }
}
