package com.sjzd.vo;

import com.sjzd.PageSizeConfig;

import java.io.Serializable;

/**
 * 该类仅为后台博客详情页需要展示和获取的属性特别定制
 */
public class BlogQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final long size = PageSizeConfig.DEFAULT_ADMIN_BLOG_PAGE_SIZE;

    /**
     *  mybatis-plus 默认初始也为1
     *  这里的默认初始页同样设为1，便于后续处理
     */
    private long currentPage = 1L;

    private Integer typeId;

    private String title;

    private boolean recommend;

    private boolean published;

    public BlogQuery() {
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        if (currentPage != null) {
            this.currentPage = currentPage;
        }
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }
}
