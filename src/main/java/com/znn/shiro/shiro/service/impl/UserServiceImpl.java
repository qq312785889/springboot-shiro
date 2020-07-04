package com.znn.shiro.shiro.service.impl;

import com.znn.shiro.shiro.domain.User;
import com.znn.shiro.shiro.mapper.UserMapper;
import com.znn.shiro.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findById(int id) {
        return userMapper.findById(id);
    }

    @Override
    public User findByName(String name) {
        return userMapper.findByName(name);
    }
}
