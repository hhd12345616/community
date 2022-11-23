package com.example.community.service.impl;

import com.example.community.entity.CommonResult;
import com.example.community.entity.ResponseStatusCode;
import com.example.community.entity.User;
import com.example.community.service.ILoginService;
import com.example.community.utils.JwtUtil;
import com.example.community.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public CommonResult login(User user) {
        CommonResult commonResult = new CommonResult();
        //验证登录
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        //验证信息不通过
        if (Objects.isNull(authentication)){
            commonResult.setCode(ResponseStatusCode.AUTHENTICATION_FAIL);
            commonResult.setMessage("登录失败");
        }
        //userid生成jwt并存进redis
        User user1 =(User)authentication.getPrincipal();
        String userId=user1.getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);
        redisCache.setCacheObject(userId,user1);
        //登陆成功返回信息
        commonResult.setCode(ResponseStatusCode.SUCCESS);
        commonResult.setMessage("登陆成功");
        commonResult.setData(map);
        return commonResult;

    }

    @Override
    public CommonResult logout() {
        CommonResult commonResult = new CommonResult();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        Long userid = user.getId();
        redisCache.deleteObject(userid.toString());
        User user1 = redisCache.getCacheObject(userid.toString());
            commonResult.setCode(ResponseStatusCode.SUCCESS);
            commonResult.setMessage("注销成功");
        return commonResult;
    }
}
