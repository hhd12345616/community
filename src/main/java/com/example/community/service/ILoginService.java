package com.example.community.service;

import com.example.community.entity.CommonResult;
import com.example.community.entity.ResponseStatusCode;
import com.example.community.entity.User;
import org.springframework.stereotype.Service;


public interface ILoginService {

    public CommonResult login(User user);

    public CommonResult logout();
}
