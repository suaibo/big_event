package com.suai.controller;

import com.suai.pojo.Result;
import com.suai.pojo.User;
import com.suai.service.UserService;
import com.suai.utils.JwtUtil;
import com.suai.utils.PasswordUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        //查询用户名是否被占用
        User byUsername = userService.findByUsername(username);
        if(byUsername != null){
            return Result.error("用户名被占用");
        }else{
            userService.register(username, password);
            return Result.success();
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$")String password){
        User byUsername = userService.findByUsername(username);
        if(byUsername == null ){
         return Result.error("用户名错误");
        }
        if(PasswordUtils.matchesPassword(password, byUsername.getPassword())){
            //登录成功
            Map<String,Object> map = new HashMap<>();
            map.put("username",byUsername.getUsername());
            map.put("id",byUsername.getId());
            String token = JwtUtil.genToken(map);
            //姜token存入redis,过期时间设置为1小时，与token过期时间相同
            redisTemplate.opsForValue().set(token,token,1, TimeUnit.HOURS);
            return Result.success(token);
        }

        return Result.error("密码错误");
    }

    @GetMapping("/userinfo")
    public Result<User> getUserInfo(@RequestHeader(name = "Authorization") String token){
        Map<String, Object> userMap = JwtUtil.verifyToken(token);
        String username = (String)userMap.get("username");
        return Result.success(userService.findByUsername(username));
    }

    @PutMapping("/update")
    public Result updateUserBaseInfo(@RequestBody User user){
        userService.updateUserBaseInfo(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateUserAvatar(String avatarUrl){
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updateUserPwd(@RequestHeader(name = "Authorization")String token,String oldPwd, @Pattern(regexp = "^\\S{5,16}$") String newPwd,@Pattern(regexp = "^\\S{5,16}$") String re_pwd){
        Map<String, Object> stringObjectMap = JwtUtil.verifyToken(token);
        String username = (String)stringObjectMap.get("username");
        User user = userService.findByUsername(username);
        if(PasswordUtils.matchesPassword(oldPwd, user.getPassword())){
            user.setPassword(newPwd);
            userService.updateUserBaseInfo(user);
            return Result.success();
        }
        else{
            return Result.error("原密码错误");
        }
    }
}
