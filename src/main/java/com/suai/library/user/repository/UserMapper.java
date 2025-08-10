package com.suai.library.user.repository;

import com.suai.library.user.model.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    //查询
    @Select("SELECT * FROM user WHERE username = #{username}")
    public User findByUsername(String username);
    //注册新用户
    @Insert("INSERT INTO user(username,password,create_time,update_time) VALUES(#{username},#{password},NOW(),NOW())")
    public void register(String username,String password);
    //更新
    @Update("UPDATE user SET email = #{email}, nickname = #{nickname}, update_time = NOW() WHERE username = #{username}")
    public void updateUserBaseInfo(User user);
}