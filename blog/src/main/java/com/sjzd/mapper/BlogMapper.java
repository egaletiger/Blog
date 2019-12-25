package com.sjzd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjzd.pojo.Blog;
import com.sjzd.vo.BlogQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-12-08
 */
public interface BlogMapper extends BaseMapper<Blog> {
    /**
     *  后台方法
     */
    List<Blog> listBlogByBlogQuery(BlogQuery blogQuery);

    Long countListBlogByBlogQuery(BlogQuery blogQuery);

    /**
     *  前端方法（要求必须是已发布）
     */
    List<Blog> listBlog(@Param("page") Page<Blog> page);

    @Select("select id, title from blog where recommend = true limit 0,${size}")
    List<Blog> listRecommendBlog(Long size);

    IPage<Blog> listBlogByQuery(@Param("page") Page<Blog> page, @Param("query") String query);

    Blog findBlogById(Integer id);

    List<Blog> listBlogByTypeId(@Param("page") Page<Blog> page, @Param("typeId") Integer typeId);

    List<Blog> listBlogByTagId(@Param("page") Page<Blog> page, @Param("tagId")Integer tagId);

    @Select("select date_format(update_time, '%Y') as year from blog group by year order by year desc")
    List<String> listYearGroup();

    @Select("select id, title, flag, date_format(update_time, '%c月%e日') update_time from blog where date_format(update_time,'%Y') = #{year}")
    List<Blog> listBlogByYear(String year);
}
