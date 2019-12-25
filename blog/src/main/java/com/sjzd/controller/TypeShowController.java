package com.sjzd.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjzd.PageSizeConfig;
import com.sjzd.pojo.Blog;
import com.sjzd.pojo.Type;
import com.sjzd.service.IBlogService;
import com.sjzd.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *  类别请求处理
 */
@Controller
public class TypeShowController {

    @Autowired
    private ITypeService typeService;

    @Autowired
    private IBlogService blogService;

    /**
     *  根据分类的id返回对应的博客
     * @param id
     * @param currentPage
     * @param model
     * @return
     */
    @GetMapping("/types/{id}")
    public String types(@PathVariable Integer id,
                        @RequestParam(name = "currentPage",defaultValue = "1") String currentPage,
                        Model model) {
        List<Type> types = typeService.listTop(1000L);
        if (id == -1) {
            id = types.get(0).getId();
        }
        Page<Blog> blogPage = (Page<Blog>)blogService.listByTypeId(id, Long.parseLong(currentPage), PageSizeConfig.DEFAULT_TRAVELLER_BLOG_PAGE_SIZE);
        model.addAttribute("types", types);
        model.addAttribute("blogPage", blogPage);
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
