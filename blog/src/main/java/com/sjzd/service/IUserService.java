package com.sjzd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sjzd.pojo.User;

/**
 * <p>
 *  用户服务类
 * </p>
 *
 * @author 
 * @since 2019-12-08
 */
public interface IUserService extends IService<User> {
    User checkUser(User user);

    Integer updateAvatar(User user);
}
