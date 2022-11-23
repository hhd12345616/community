package com.example.community.controller;

import com.example.community.entity.CommonResult;
import com.example.community.entity.User;
import com.example.community.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    private IRegisterService service;

    @PostMapping("/user/register")
    public CommonResult register(@RequestBody User user){
        return service.register(user);
    }
}
