package com.znn.shiro.shiro.service;

import com.znn.shiro.shiro.domain.User;

public interface UserService {
    User findById(int id);
    User findByName(String name);
}
