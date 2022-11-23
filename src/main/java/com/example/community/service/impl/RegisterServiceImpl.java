package com.example.community.service.impl;

import com.example.community.entity.CommonResult;
import com.example.community.entity.ResponseStatusCode;
import com.example.community.entity.User;
import com.example.community.mapper.UserMapper;
import com.example.community.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RegisterServiceImpl implements IRegisterService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public CommonResult register(User user) {
        CommonResult commonResult = new CommonResult();
        String password = user.getPassword();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));
        user.setStatus("0");
        user.setUserType("1");
        user.setCreateTime(new Date());
        user.setDelFlag(new Integer(0));
        int row = userMapper.insert(user);
        if (row ==1){
            commonResult.setCode(ResponseStatusCode.SUCCESS);
            commonResult.setMessage("注册成功");
        }else{
            commonResult.setCode(ResponseStatusCode.REGISTER_FAIL);
            commonResult.setMessage("注册失败");
        }
        return commonResult;
    }
}
