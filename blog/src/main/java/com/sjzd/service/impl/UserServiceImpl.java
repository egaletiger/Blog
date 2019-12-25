package com.sjzd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjzd.mapper.UserMapper;
import com.sjzd.pojo.User;
import com.sjzd.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author 
 * @since 2019-12-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUser(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername())
                    .eq("password", user.getPassword());

        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public Integer updateAvatar(User user) {
        return userMapper.updateById(user);
    }
}
