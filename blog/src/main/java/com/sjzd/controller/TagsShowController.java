package com.sjzd.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjzd.PageSizeConfig;
import com.sjzd.pojo.Blog;
import com.sjzd.pojo.Tag;
import com.sjzd.service.IBlogService;
import com.sjzd.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *  标签页请求处理
 */
@Controller
public class TagsShowController {
    @Autowired
    private ITagService tagService;

    @Autowired
    private IBlogService blogService;

    /**
     *  根据标签id返回对应博客
     * @param id
     * @param currentPage
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}")
    public String types(@PathVariable Integer id,
                        @RequestParam(name = "currentPage",defaultValue = "1") String currentPage,
                        Model model) {
        List<Tag> tags = tagService.listTop(1000L);
        if (id == -1) {
            id = tags.get(0).getId();
        }
        Page<Blog> blogPage = (Page<Blog>)blogService.listByTagId(id, Long.parseLong(currentPage), PageSizeConfig.DEFAULT_TRAVELLER_BLOG_PAGE_SIZE);
        model.addAttribute("tags", tags);
        model.addAttribute("blogPage", blogPage);
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
