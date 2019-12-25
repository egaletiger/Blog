package com.sjzd.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjzd.PageSizeConfig;
import com.sjzd.pojo.Blog;
import com.sjzd.pojo.Tag;
import com.sjzd.pojo.Type;
import com.sjzd.service.IBlogService;
import com.sjzd.service.ITagService;
import com.sjzd.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *  index页面和一些共有请求的处理
 */
@Controller
public class IndexController {

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ITypeService typeService;

    @Autowired
    private ITagService tagService;

    /**
     *  博客首页面
     * @param currentPage
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(@RequestParam(name = "currentPage", defaultValue = "1") String currentPage,
                                Model model) {
        Page<Blog> blogPage = (Page<Blog>) blogService.listBlog(Long.parseLong(currentPage), PageSizeConfig.DEFAULT_TRAVELLER_BLOG_PAGE_SIZE);
        List<Tag> tags = tagService.listTop(PageSizeConfig.DEFAULT_TRAVELLER_TAG_PAGE_SIZE);
        List<Type> types = typeService.listTop(PageSizeConfig.DEFAULT_TRAVELER_TYPE_PAGE_SIZE);
        List<Blog> recommendBlogs = blogService.listRecommendBlog(PageSizeConfig.DEFAULT_TRAVELLER_BLOG_TOP_PAGE_SIZE);

        model.addAttribute("blogPage", blogPage);
        model.addAttribute("tags", tags);
        model.addAttribute("types", types);
        model.addAttribute("recommendBlogs", recommendBlogs);
        return "index";
    }

    /**
     *  处理关键字搜索博客请求
     * @param currentPage
     * @param query 关键字字符串
     * @param model
     * @return
     */
    @RequestMapping("/search")
    public String search(@RequestParam(name = "currentPage", defaultValue = "1") String currentPage,
                        @RequestParam String query, Model model) {
        Page<Blog> blogPage = (Page<Blog>) blogService.listBlogByQuery(Long.parseLong(currentPage),
                                    PageSizeConfig.DEFAULT_TRAVELLER_BLOG_TOP_PAGE_SIZE,
                                    query);

        model.addAttribute("blogPage", blogPage);
        model.addAttribute("query", query);
        return "search";
    }

    /**
     *  处理博客id查找博客请求
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Integer id, Model model) {
        Blog blog = blogService.findBlogAndConvertById(id);
        model.addAttribute("blog", blog);
        return "blog";
    }

    /**
     *  处理页面底部导航栏 --> 最新博客栏的请求处理
     * @param model
     * @return
     */
    @GetMapping("/footer/fresh")
    public String footer(Model model) {
        List<Blog> blogs = blogService.listRecommendBlog(3L);
        model.addAttribute("blogs", blogs);
        return "_fragments :: footer-fresh-div";
    }
}
