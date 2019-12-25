package com.sjzd.pojo;

import com.baomidou.mybatisplus.annotation.TableField;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 *  博客类型实体类
 */
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    /**
     * 类型名称
     */
    @NotBlank(message = "类型名不能为空")
    private String name;

    /**
     *  属于该类的所有博客
     */
    @TableField(exist = false)
    private List<Blog> blogs;

    /**
     *  带有改标签的博客数量
     */
    @TableField(exist = false)
    private Integer blogSize;

    public Type() {
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
