package com.sjzd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *  关于我页面的请求处理
 */
@Controller
public class AboutController {

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
