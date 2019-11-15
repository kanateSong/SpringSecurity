package com.ljj.service;

import com.ljj.dao.UserDao;
import com.ljj.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service(value = "userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        /*if (userName.indexOf(CommonConstants.SPLIT_SYMBOL) > 0) {
            userName = userName.split(CommonConstants.SPLIT_SYMBOL)[0];
        }
        User user = userDao.findByUsernameAndDelFlag(userName,1);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return UserDetailConverter.convert(user);*/
        return null;
    }

    @Override
    public void saveUser(User user) {
        /*User check = userDao.findByUsername(user.getUsername());
        if(check != null){
            throw new UsernameNotFoundException("该用户名已存在。");
        }
        user.setUserId(userDao.getUserId());
        user.setPassword("{bcrypt}"+new BCryptPasswordEncoder().encode(user.getPassword()));
        userDao.save(user);*/
    }

    @Override
    public User findByUserName(String userName) {
        //return userDao.findByUsernameAndDelFlag(userName,1);
        return null;
    }


    private static class UserDetailConverter {
        /*static UserDetails convert(User user) {

            return new MyUserDetails(user);
        }*/
    }
}
