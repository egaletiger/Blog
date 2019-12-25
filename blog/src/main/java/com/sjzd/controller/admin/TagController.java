package com.sjzd.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjzd.PageSizeConfig;
import com.sjzd.pojo.Tag;
import com.sjzd.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 *  标签后台请求处理
 */
@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private ITagService tagService;

    /**
     *  分页形式查询所有标签操作
     * @param currentPage
     * @param model
     * @return
     */
    @GetMapping("/tags")
    public String tags(@RequestParam(name = "currentPage", defaultValue = "1") String currentPage,
                     Model model) {
        Page<Tag> tagIPage = (Page<Tag>) tagService.listTag(Long.parseLong(currentPage),
                            PageSizeConfig.DEFAULT_ADMIN_TAG_PAGE_SIZE);
        model.addAttribute("tags", tagIPage);
        return "admin/tags";
    }

    /**
     * 请求添加操作
     * @param model
     * @return
     */
    @GetMapping("/tags/input")
    public String tagInput(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    /**
     * 执行添加操作
     * @param tag
     * @param redirectAttributes
     * @param result
     * @return
     */
    @PostMapping("/tags/add")
    public String tagAdd(@Valid Tag tag, RedirectAttributes redirectAttributes,
                          BindingResult result) {
        if (hasAlready(tag)) {
            result.rejectValue("name","nameError","新增失败,已有该标签名称");
            return "admin/tags-input";
        }

        if (result.hasErrors()) {
            return "admin/tags-input";
        }

        Integer flag = tagService.saveTag(tag);
        if (flag < 1) {
            redirectAttributes.addFlashAttribute("message", "操作失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "添加成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     *  请操作求修改
     * @param id
     * @return
     */
    @GetMapping("/tags/{id}/edit")
    public String tagInput(@PathVariable Integer id, Model model){
        Tag tag = tagService.findTagById(id);
        model.addAttribute("tag", tag);
        return "admin/tags-input";
    }

    /**
     * 执行修改操作
     * @param tag
     * @param redirectAttributes
     * @param result
     * @return
     */
    @PostMapping("/tags/edit/{id}")
    public String tagEdit(@PathVariable Integer id, @Valid Tag tag,
                          RedirectAttributes redirectAttributes,
                          BindingResult result) {

        if (hasAlready(tag)) {
            result.rejectValue("name","nameError","新增失败,已有该标签名称");
            return "admin/tags-input";
        }

        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        tag.setId(id);
        Integer flag = tagService.updateTag(tag);
        if (flag < 1) {
            redirectAttributes.addFlashAttribute("message", "操作失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     *  删除操作
     * @param id
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/tags/{id}/delete")
    public String tagDelete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Integer flag = tagService.deleteTag(id);
        if (flag < 1) {
            redirectAttributes.addFlashAttribute("message", "操作失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "删除成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     *  防止重复插入
     * @param tag
     * @return
     */
    public boolean hasAlready(Tag tag) {
        return tagService.findTagByName(tag.getName()) != null;
    }
}
