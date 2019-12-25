package com.sjzd.pojo;

import com.baomidou.mybatisplus.annotation.TableField;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 博客实体类
 */
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    /**
     * 标题
     */
    @NotBlank(message = "标题名不能为空")
    private String title;

    /**
     * 博客内容
     */
    @NotBlank(message = "博客内容不能为空")
    private String content;

    /**
     *  文章标志（原创， 转载， 翻译）
     */
    @NotBlank(message = "博客标志位不能为空")
    private String flag;

    /**
     * 博客内容简述
     */
    private String description;

    /**
     * 文章开头的图片
     */
    private String firstPicture;

    /**
     * 浏览次数
     */
    private Integer views;

    /**
     * 是否可以赞赏
     */
    private Boolean appreciation;

    /**
     * 是否开启转发版权显示
     */
    private Boolean shareStatement;

    /**
     * 是否可评论
     */
    private Boolean commentable;

    /**
     * 是否已发布
     */
    private Boolean published;

    /**
     * 是否被推荐
     */
    private Boolean recommend;

    /**
     * 创建日期
     */
    private String createTime;

    /**
     * 更改日期
     */
    private String updateTime;

    /**
     *  作者id
     */
    private Integer userId;

    /**
     *  类型id
     */
    @NotNull(message = "必须为博客划分类型")
    private Integer typeId;

    /**
     *  标签id
     */
    private String tagIds;

    /**
     *  评论id
     */
//    private Integer commentId;

    /**
     *  作者
     */
    @TableField(exist = false)
    private User author;

    /**
     *  类型
     */
    @TableField(exist = false)
    private Type type;

    /**
     * 标签
     */
    @TableField(exist = false)
    private List<Tag> tags;

    /**
     *  评论
     */
    @TableField(exist = false)
    private List<Comment> comments;


    public Blog() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Boolean getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(Boolean appreciation) {
        this.appreciation = appreciation;
    }

    public Boolean getShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(Boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public Boolean getCommentable() {
        return commentable;
    }

    public void setCommentable(Boolean commentable) {
        this.commentable = commentable;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    //    public Integer getCommentId() {
//        return commentId;
//    }
//
//    public void setCommentId(Integer commentId) {
//        this.commentId = commentId;
//    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
