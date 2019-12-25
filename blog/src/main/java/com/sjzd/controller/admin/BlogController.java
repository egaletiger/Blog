package com.sjzd.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjzd.pojo.Blog;
import com.sjzd.pojo.Type;
import com.sjzd.pojo.User;
import com.sjzd.service.IBlogService;
import com.sjzd.service.ITagService;
import com.sjzd.service.ITypeService;
import com.sjzd.util.TimeUtils;
import com.sjzd.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 *  博客管理后台服务处理博客的增删改查
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ITypeService typeService;

    @Autowired
    private ITagService tagService;

    /**
     *  分页式查询博客操作
     * @param blogQuery
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String blogs(BlogQuery blogQuery, Model model) {
        Page<Blog> blogPage = (Page<Blog>) blogService.listBlogByBlogQuery(blogQuery);
        List<Type> list = typeService.list();
        model.addAttribute("blogPage", blogPage);
        model.addAttribute("types", list);
        return "admin/blogs";
    }

    /**
     *  按指定条件搜索相应博客操作
     * @param blogQuery
     * @param model
     * @return
     */
    @PostMapping("/blogs/search")
    public String search(BlogQuery blogQuery, Model model) {
        IPage<Blog> blogIPage = blogService.listBlogByBlogQuery(blogQuery);
        model.addAttribute("blogPage", blogIPage);
        return "admin/blogs :: blogTable";
    }

    /**
     *  请求添加操作
     * @param model
     * @return
     */
    @GetMapping("/blogs/input")
    public String blogInput(Model model) {
        model.addAttribute("blog", new Blog());
        model.addAttribute("tags", tagService.list());
        model.addAttribute("types", typeService.list());
        return "admin/blogs-input";
    }

    /**
     *  请求编辑操作
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blogs/{id}/edit")
    public String blogInput(@PathVariable Integer id, Model model) {
        Blog blog = blogService.findBlogById(id);
        blog.setTags(tagService.ListTagByTagIds(blog.getTagIds()));
        blog.setType(typeService.findTypeById(blog.getId()));
        model.addAttribute("blog", blog);
        model.addAttribute("tags", tagService.list());
        model.addAttribute("types", typeService.list());
        return "admin/blogs-input";
    }

    /**
     * 修改博客操作
     * @param id
     * @param blog
     * @param result
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/blogs/edit/{id}")
    public String blogEdit(@PathVariable Integer id, @Valid Blog blog, BindingResult result,
                           RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "恶意添加博客将不予处理！请按规范发布博客");
            return "redirect:/admin/blogs";
        }
        // 修改更新日期
        blog.setId(id);
        blog.setUpdateTime(TimeUtils.getNowTimeString());
        Integer flag = blogService.updateBlog(blog);
        if (flag > 0) {
            redirectAttributes.addFlashAttribute("message", "操作成功！");
        } else {
            redirectAttributes.addFlashAttribute("message", "更新失败");
        }
        return "redirect:/admin/blogs";
    }


    /**
     * 添加博客操作
     * @param blog
     * @param result
     * @param session
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/blogs/add")
    public String blogAdd(@Valid Blog blog, BindingResult result,
                          HttpSession session, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "恶意添加博客将不予处理！请按规范发布博客");
            return "redirect:/admin/blogs";
        }
        // 添加初始创建日期和修改日期
        String nowTimeString = TimeUtils.getNowTimeString();
        blog.setCreateTime(nowTimeString);
        blog.setUpdateTime(nowTimeString);
        // 添加作者id
        User author = (User)session.getAttribute("user");
        blog.setUserId(author.getId());
        // 默认浏览量
        blog.setViews(0);

        Integer flag = blogService.saveBlog(blog);
        if (flag > 0) {
            redirectAttributes.addFlashAttribute("message", "操作成功！");
        } else {
            redirectAttributes.addFlashAttribute("message", "添加失败");
        }
        return "redirect:/admin/blogs";
    }

    /**
     *  根据id删除博客
     * @param id
     * @param redirectAttributes
     * @return
     */
   @GetMapping("/blogs/delete/{id}")
    public String blogDelete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
       Integer flag = blogService.deleteBlog(id);
       if (flag > 0) {
           redirectAttributes.addFlashAttribute("message", "操作成功！");
       } else {
           redirectAttributes.addFlashAttribute("message", "删除失败");
       }
       return "redirect:/admin/blogs";
   }

}
