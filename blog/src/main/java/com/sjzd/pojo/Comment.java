package com.sjzd.pojo;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.List;

/**
 *  评论实体类
 */
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    private  int id;

    /**
     * 评论用户昵称
     */
    private String nickName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 评论时间
     */
    private String createTime;

    /**
     *  是否是管理员的评论
     */
    private boolean isAdminComment;

    /**
     *  该评论回复的评论的id（不针对任何回复的评论 pid=-1）
     */
    private Integer pid;

    /**
     *  该评论回复的评论（不针对任何回复的评论 pid=-1）
     */
    @TableField(exist = false)
    private Comment parentComment;

    /**
     *  被评论的博客id
     */
    @TableField(exist = false)
    private Integer blogId;

    /**
     *  该评论收到的回复
     */
    @TableField(exist = false)
    private List<Comment> replyComments;

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public boolean getIsAdminComment() {
        return isAdminComment;
    }

    public void setIsAdminComment(boolean adminComment) {
        isAdminComment = adminComment;
    }
}
