package com.suai.library.common.interceptors;

import cn.hutool.core.bean.BeanUtil;
import com.suai.library.common.utils.UserHolder;
import com.suai.library.user.model.dto.UserDto;
import com.suai.library.user.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RefreshTokenInterceptor implements HandlerInterceptor {

    private StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        String redisToken = stringRedisTemplate.opsForValue().get(token);
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(token);
        UserDto userDto = BeanUtil.fillBeanWithMap(entries, new UserDto(), false);
        UserHolder.saveUser(userDto);
        stringRedisTemplate.expire(token, 30, TimeUnit.MINUTES);
        return true;
    }
}
