package com.suai.library.common.utils;


import com.suai.library.user.model.dto.UserDto;

public class UserHolder {
    private static final ThreadLocal<UserDto> userThreadLocal = new ThreadLocal<UserDto>();

    public static void saveUser(UserDto user) {
        userThreadLocal.set(user);
    }

    public static UserDto getUser() {
        return userThreadLocal.get();
    }

    public static void removeUser() {
        userThreadLocal.remove();
    }
}
