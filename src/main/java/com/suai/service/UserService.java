package com.suai.service;

import com.suai.pojo.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface UserService {
    //注册
    public void register(String username,String password);
    //根据用户名查询用户
    public User findByUsername(String username);
    //更新用户
    public void updateUserBaseInfo(User user);
}
