package com.sjzd.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sjzd.pojo.Blog;
import com.sjzd.vo.BlogQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  博客服务类
 * </p>
 *
 * @author 
 * @since 2019-12-08
 */
public interface IBlogService extends IService<Blog> {

    Integer saveBlog(Blog blog);

    Integer deleteBlog(Integer id);

    Integer updateBlog(Blog newBlog);

    IPage<Blog> listBlogByBlogQuery(BlogQuery blogQuery);

    Blog findBlogById(Integer id);

    IPage<Blog> listBlog(Long currentPage, Long size);

    List<Blog> listRecommendBlog(Long size);

    IPage<Blog> listBlogByQuery(Long currentPage, Long size, String query);

    /**
     *  主要是将博客内容（markDown格式）转换为html
     * @param id
     * @return
     */
    Blog findBlogAndConvertById(Integer id);

    IPage<Blog> listByTypeId(Integer typeId, Long currentPage, Long size);

    IPage<Blog> listByTagId(Integer tagId, Long currentPage, Long size);

    /**
     *  博客归档处理（按照年来划分）
     * @return
     */
    Map<String, List<Blog>> archiveBlog();
}
