package com.example.community.handler;

import com.alibaba.fastjson.JSON;
import com.example.community.entity.CommonResult;
import com.example.community.entity.ResponseStatusCode;
import com.example.community.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //处理异常
        CommonResult responseResult = new CommonResult();
        responseResult.setCode(ResponseStatusCode.NOT_LOGIN);
        responseResult.setMessage("请重新登录");
        String json = JSON.toJSONString(responseResult);
        WebUtils.renderString(httpServletResponse,json);
    }
}
