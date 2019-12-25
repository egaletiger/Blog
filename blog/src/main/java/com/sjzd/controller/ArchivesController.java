package com.sjzd.controller;

import com.sjzd.pojo.Blog;
import com.sjzd.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * 归档页面请求处理
 */
@Controller
public class ArchivesController {

    @Autowired
    private IBlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        Map<String, List<Blog>> archivesMap = blogService.archiveBlog();
        int count = blogService.count(); // mybatis-plus 自带
        model.addAttribute("archivesMap", archivesMap);
        model.addAttribute("count", count);
        return "archives";
    }
}
