package com.lzy.cloud.microservicesimpleprovideruser.controller;

import com.lzy.cloud.microservicesimpleprovideruser.dao.UserDao;
import com.lzy.cloud.microservicesimpleprovideruser.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        Optional<User> findOne = userDao.findById(id);
        User result = findOne.get();
        return result;
    }
}
