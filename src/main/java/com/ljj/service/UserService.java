package com.ljj.service;


import com.ljj.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * create by dyl
 * on 18-5-24.
 */
public interface UserService extends UserDetailsService {

    void saveUser(User user);

    User findByUserName(String userName);
}
