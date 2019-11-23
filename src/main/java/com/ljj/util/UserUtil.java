package com.ljj.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

/**
 * @Description: 获取springsecurity用户信息
 * @Author LeeJack
 * @Date 2019/11/23
 * @Version V1.0
 **/
public class UserUtil {
    public static String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getPrincipal().toString();
        if (!StringUtils.isEmpty(userId)) {
            int index = userId.indexOf("##");
            userId = userId.substring((index + 2), userId.length());
        }
        return userId;
    }
}
