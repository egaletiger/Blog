package com.sjzd.pojo;

import com.baomidou.mybatisplus.annotation.TableField;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 *  标签实体类
 */
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    /**
     * 标签名称
     */
    @NotBlank(message = "标签名不能为空")
    private String name;

    /**
     *  带有该标签的博客
     */
    @TableField(exist = false)
    private List<Blog> blogs;

    /**
     *  带有改标签的博客数量
     */
    @TableField(exist = false)
    private Integer blogSize;

    public Tag() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public Integer getBlogSize() {
        return blogSize;
    }

    public void setBlogSize(Integer blogSize) {
        this.blogSize = blogSize;
    }
}
