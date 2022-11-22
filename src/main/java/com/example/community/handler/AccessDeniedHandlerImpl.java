package com.example.community.handler;

import com.alibaba.fastjson.JSON;
import com.example.community.entity.CommonResult;
import com.example.community.entity.ResponseStatusCode;
import com.example.community.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        //处理异常
        CommonResult responseResult = new CommonResult();
        responseResult.setCode(ResponseStatusCode.NOT_LOGIN);
        responseResult.setMessage("未授权");
        String json = JSON.toJSONString(responseResult);
        WebUtils.renderString(httpServletResponse,json);
    }
}
