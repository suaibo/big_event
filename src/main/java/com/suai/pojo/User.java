package com.suai.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * @author 王彬权
 */
@Data
public class User {
    @Id
    private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private String nickname;
    private String userPic;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
