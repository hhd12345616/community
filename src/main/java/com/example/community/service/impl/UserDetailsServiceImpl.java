package com.example.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.community.entity.User;
import com.example.community.mapper.MenuMapper;
import com.example.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>();
        wrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(wrapper);
        //查询不到数据则抛出异常
        if (Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }
        user.setPermissions(menuMapper.selectPermsByUserId(user.getId()));
        return user;
    }
}
