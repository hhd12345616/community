package com.example.community.controller;

import com.example.community.entity.CommonResult;
import com.example.community.entity.User;
import com.example.community.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private ILoginService service;

    @PostMapping("/user/login")
    public CommonResult login(@RequestBody User user){
        return service.login(user);
    }
}
