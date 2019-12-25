package com.sjzd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjzd.exception.NotFoundException;
import com.sjzd.mapper.BlogMapper;
import com.sjzd.mapper.BlogTagMapper;
import com.sjzd.pojo.Blog;
import com.sjzd.service.IBlogService;
import com.sjzd.util.MarkdownUtils;
import com.sjzd.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 博客 服务实现类
 * </p>
 *
 * @author 
 * @since 2019-12-08
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogTagMapper blogTagMapper;

    @Transactional
    @Override
    public Integer saveBlog(Blog blog) {
        // 设置博客内容描述
        blog.setDescription(extractDescription(blog.getContent()));
        if (blogMapper.insert(blog) < 1) {
            return 0;
        }
         // 在blog_tag表中添加对应记录
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", blog.getUserId())
                    .eq("create_time", blog.getCreateTime());
        Blog temp = blogMapper.selectOne(queryWrapper); // 获取插入博客的id

        if (null == temp) {
            throw new NotFoundException("网络繁忙请重试");
        }

        List<Integer> list = convertNumStrToIntList(blog.getTagIds());
        if (list != null) {
            blogTagMapper.insertBlogTagByBlogId(temp.getId(), list);
        }
        return 1;
    }

    @Transactional
    @Override
    public Integer deleteBlog(Integer id) {
        return blogMapper.deleteById(id);
    }

    @Transactional
    @Override
    public Integer updateBlog(Blog newBlog) {
        // 先查询原有bolg的tagIds
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "tag_ids").eq("id", newBlog.getId());
        List<Integer> old = convertNumStrToIntList(blogMapper.selectOne(queryWrapper).getTagIds());
        // 删除原有的tagIds
        if (old != null) {
            if (blogTagMapper.deleteBlogTagByBlogId(newBlog.getId(), old) < 1)
                return 0;
        }
        // 插入新的tagIds
        List<Integer> fresh = convertNumStrToIntList(newBlog.getTagIds());
        if (fresh != null) {
            if (blogTagMapper.insertBlogTagByBlogId(newBlog.getId(), fresh) < 1 )
                return 0;
        }
        // 设置博客内容描述
        newBlog.setDescription(extractDescription(newBlog.getContent()));
        // 更新博客
        return blogMapper.updateById(newBlog);
    }

    @Override
    public IPage<Blog> listBlogByBlogQuery(BlogQuery blogQuery) {
        List<Blog> blogs = blogMapper.listBlogByBlogQuery(blogQuery);
        Long total = blogMapper.countListBlogByBlogQuery(blogQuery);
        Page<Blog> blogPage = new Page<>(blogQuery.getCurrentPage(),
                               blogQuery.size, total);
        blogPage.setRecords(blogs);
        return blogPage;
    }

    @Override
    public Blog findBlogById(Integer id) {
        Blog blog = blogMapper.selectById(id);
        if (null == blog) {
            throw new NotFoundException("该资源不存在");
        }
        return blog;
    }

    @Override
    public IPage<Blog> listBlog(Long currentPage, Long size) {
        Page<Blog> page = new Page<>(currentPage, size);
        page.setRecords(blogMapper.listBlog(page));
        return page;
    }

    @Override
    public List<Blog> listRecommendBlog(Long size) {
        return blogMapper.listRecommendBlog(size);
    }

    @Override
    public IPage<Blog> listBlogByQuery(Long currentPage, Long size, String query) {
        Page<Blog> page = new Page<>(currentPage, size);
        return (IPage<Blog>) blogMapper.listBlogByQuery(page, "%" + query + "%");
    }

    @Override
    public Blog findBlogAndConvertById(Integer id) {
        Blog blog = blogMapper.findBlogById(id);
        if (null == blog) {
            throw new NotFoundException("该资源不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        b.setContent(MarkdownUtils.markdownToHtml(b.getContent()));
        // 更新博客的浏览量
        // blogMapper.updateById(blog); content内容较大需要细化
        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("views", blog.getViews() +1)
                     .eq("id", blog.getId());
        blogMapper.update(null , updateWrapper);
        return b;
    }

    @Override
    public IPage<Blog> listByTypeId(Integer typeId, Long currentPage, Long size) {
        Page<Blog> page = new Page<>(currentPage, size);
        page.setRecords(blogMapper.listBlogByTypeId(page, typeId));
        return page;
    }

    @Override
    public IPage<Blog> listByTagId(Integer tagId, Long currentPage, Long size) {
        Page<Blog> page = new Page<>(currentPage, size);
        page.setRecords(blogMapper.listBlogByTagId(page, tagId));
        return page;
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        Map<String, List<Blog>> map = new HashMap<>();
        List<String> years = blogMapper.listYearGroup();
        for (final String year : years) {
            map.put(year, blogMapper.listBlogByYear(year));
        }
        return map;
    }

    /**
     *  将可被‘,’分割的数字字符串转为整形list
     * @param s  例如： “1,2,3”
     * @return
     */
    private List<Integer> convertNumStrToIntList(String s) {
        if (s != null || !s.equals("")){
            List<Integer> list = Arrays.stream(s.split(","))
                    .map(e -> Integer.parseInt(e))
                    .collect(Collectors.toList());
            return list;
        }
        return null;
    }

    /**
     *  最多提取博客前20个字符，当博客内容不够20个字符时，
     *  有多少提取多少
     */
    private String extractDescription(String content) {
        if (content.length() > 20) {
            return content.substring(0, 21);
        } else {
            return content.substring(0,content.length());
        }
    }
}
