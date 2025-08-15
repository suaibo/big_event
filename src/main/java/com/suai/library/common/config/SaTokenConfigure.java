package com.suai.library.common.config;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {  // 重命名避免冲突

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Bean
    public SaTokenConfig getSaTokenConfig() {
        SaTokenConfig config = new SaTokenConfig();
        config.setTokenName("Authorization")
                .setTimeout(60 * 60) // 1小时有效期
                .setActivityTimeout(30 * 60) // 30分钟无操作过期
                .setIsShare(true)
                .setIsReadHeader(true)
                .setIsReadBody(false)
                .setIsReadCookie(false)
                .setTokenStyle("simple-uuid");
        return config;
    }

    // 注册 Sa-Token 注解拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/user/register");
    }
}
