package com.sjzd.controller.admin;

import com.sjzd.pojo.User;
import com.sjzd.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 更新头像
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public String updateIcon(MultipartFile file, HttpSession session){
        if (null != file) {
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            File destination = new File("F:/upload/admin" + suffix);
            if (!destination.exists()) {
                destination.mkdir();
            }
            try {
                file.transferTo(destination);
                User user = (User)session.getAttribute("user");
                user.setAvatar("admin" + suffix);
                userService.updateAvatar(user);
            } catch (IOException e) {
                return "更新失败";
            }
        }
        return "更新成功";
    }
}
