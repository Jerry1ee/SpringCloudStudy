package com.lzy.cloud.microservicesimpleprovideruser.controller;

import com.lzy.cloud.microservicesimpleprovideruser.dao.UserDao;
import com.lzy.cloud.microservicesimpleprovideruser.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserDao userDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            UserDetails user = (UserDetails) principal;
            Collection<? extends GrantedAuthority> collection = user.getAuthorities();
            for(GrantedAuthority c : collection){
                //打印当前登录用户的信息
                UserController.LOGGER.info("当前用户是{},角色是{}",user.getUsername(),c.getAuthority());
            }
        }else{

        }
        Optional<User> findOne = userDao.findById(id);
        User result = findOne.get();
        return result;
    }
}
