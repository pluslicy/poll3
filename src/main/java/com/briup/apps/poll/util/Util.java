package com.briup.apps.poll.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.briup.apps.poll.bean.User;

/**
 * Created by sang on 2017/12/20.
 */
public class Util {
    public static User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}
