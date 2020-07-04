package com.znn.shiro.shiro.mapper;

import com.znn.shiro.shiro.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User findByName(String name);

    User findById(Integer id);

}
