package com.example.community;

import com.example.community.entity.User;
import com.example.community.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class CommunityApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        User user= userMapper.selectOne(null);
        System.out.println(user);
        PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("123")+"\t"+passwordEncoder.matches("123",user.getPassword()));
    }

}
