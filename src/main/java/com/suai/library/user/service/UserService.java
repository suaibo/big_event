package com.suai.library.user.service;

import com.suai.library.user.model.entity.User;

public interface UserService {
    //注册
    public void register(String username,String password);
    //根据用户名查询用户
    public User findByUsername(String username);
    //更新用户
    public void updateUserBaseInfo(User user);
}
