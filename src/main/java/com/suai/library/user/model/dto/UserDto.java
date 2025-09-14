package com.suai.library.user.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class UserDto {
    @Id
    private Integer id;
    private String username;
    private String email;
    private String nickname;
    private String userPic;
    //角色
    private String role = "user";
}
