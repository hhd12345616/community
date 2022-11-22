package com.example.community.filter;

import com.example.community.entity.CommonResult;
import com.example.community.entity.ResponseStatusCode;
import com.example.community.entity.User;
import com.example.community.utils.JwtUtil;
import com.example.community.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class TokenAuthenticationFiler extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //从请求头获取token
        String token = request.getHeader("token");
        //无token直接放行
        if (!StringUtils.hasText(token)){
            filterChain.doFilter(request,response);
            return;
        }
        //有token，更具token值取出redis中对应的user
        String userId =null;
        try{
            Claims claims = JwtUtil.parseJWT(token);
            userId =claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        User user = redisCache.getCacheObject(userId);
        //没有取到对应的user，登陆超时或未登录
        if (Objects.isNull(user)){
            throw new RuntimeException("用户未登录");
        }
        //将存有user信息和对应权限信息的authentication存入SercurityContextHolder,提供后续过滤器使用
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
