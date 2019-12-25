package com.sjzd.controller.admin;

import com.sjzd.pojo.User;
import com.sjzd.service.IUserService;
import com.sjzd.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 *  后台登录请求管理
 */
@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private IUserService userService;

    /**
     *  跳转登录页面
     * @return
     */
    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    /**
     *  验证登录信息
     * @param user
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("/login")
    public String login(@Valid User user, HttpSession session, RedirectAttributes attributes) {
        user.setPassword(MD5Utils.code(user.getPassword()));
        user = userService.checkUser(user);
        if (null != user) {
            session.setAttribute("user", user);
            return "admin/index";
        } else {
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "redirect:/admin";
        }
    }

    /**
     *  退出登录
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
