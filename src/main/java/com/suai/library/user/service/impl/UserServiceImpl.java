package com.suai.library.user.service.impl;

import com.suai.library.user.repository.UserMapper;
import com.suai.library.user.model.entity.User;
import com.suai.library.user.service.UserService;
import com.suai.library.common.utils.PasswordUtils;
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
        userMapper.register(username,encryptedPassword,"user");
    }

    public void registerAdmin(String username, String password) {
        String encryptedPassword = PasswordUtils.encodePassword(password);
        userMapper.register(username, encryptedPassword, "admin");
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
