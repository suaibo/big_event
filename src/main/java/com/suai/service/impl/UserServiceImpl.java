package com.suai.service.impl;

import com.suai.mapper.UserMapper;
import com.suai.pojo.User;
import com.suai.service.UserService;
import com.suai.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(String username, String password) {
        //加密密码
        String encryptedPassword = PasswordUtils.encodePassword(password);
        //注册进入数据库
        userMapper.register(username,encryptedPassword);
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
    //更新
    @Override
    public void updateUserBaseInfo(User user) {
        userMapper.updateUserBaseInfo(user);
    }
}
