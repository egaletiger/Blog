package com.sjzd.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjzd.PageSizeConfig;
import com.sjzd.pojo.Type;
import com.sjzd.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 *  分类后台请求处理
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private ITypeService typeService;

    /**
     *  分页形式查询分类操作
     * @param currentPage
     * @param model
     * @return
     */
    @GetMapping ("/types")
    public String types(@RequestParam(name = "currentPage",defaultValue = "1") String currentPage,
                       Model model) {
        Page<Type> types = (Page<Type>)typeService.listType(Long.parseLong(currentPage), PageSizeConfig.DEFAULT_ADMIN_TYPE_PAGE_SIZE);
        model.addAttribute("types", types);
        return "admin/types";
    }

    /**
     *  请求新增分类操作
     * @return
     */
    @GetMapping("/types/input")
    public String typeInput(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    /**
     *  新增分类操作,并附加消息提示 0--成功， 1--失败
     */
    @PostMapping("/types/add")
    public String typeAdd(@Valid Type type, RedirectAttributes redirectAttributes,
                          BindingResult result) {
        if (hasAlready(type)) {
            result.rejectValue("name","nameError","新增失败,已有该分类");
            return "admin/types-input";
        }

        if (result.hasErrors()) {
            return "admin/types-input";
        }

        Integer flag = typeService.saveType(type);
        if (flag < 1) {
            redirectAttributes.addFlashAttribute("message", "操作失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "添加成功");
        }
        return "redirect:/admin/types";
    }

    /**
     *  请求修改分类操作
     */
    @GetMapping("/types/{id}/input")
    public String typeInput(@PathVariable Integer id, Model model) {
        Type type = typeService.findTypeById(id);
        model.addAttribute("type", type);
        return "admin/types-input";
    }

    /**
     *  修改分类操作,并附加消息提示 0--成功， 1--失败
     */
    @PostMapping("/types/edit/{id}")
    public String typeEdit(@PathVariable Integer id, @Valid Type type,  BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (hasAlready(type)) {
            result.rejectValue("name","nameError","新增失败,已有该分类");
            return "admin/types-input";
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }

        type.setId(id);
        Integer flag = typeService.updateType(type);
        if (flag < 1) {
            redirectAttributes.addFlashAttribute("message", "操作失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/types";
    }

    /**
     *  删除分类操作
     */
    @GetMapping("types/{id}/delete")
    public String listAll(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Integer flag = typeService.deleteType(id);
        if (flag < 1) {
            redirectAttributes.addFlashAttribute("message", "操作失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "删除成功");
        }
        return "redirect:/admin/types";
    }


    /**
     *  防止分类重复
     */
    public boolean hasAlready(Type type) {
        return typeService.findTypeByName(type.getName()) != null;
    }
}
