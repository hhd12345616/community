package com.example.community.service.impl;

import com.example.community.entity.Comment;
import com.example.community.entity.CommonResult;
import com.example.community.entity.ResponseStatusCode;
import com.example.community.mapper.CommentMapper;
import com.example.community.service.ICommentService;
import com.example.community.utils.JwtUtil;
import com.example.community.utils.SensitiveWordFilter;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public CommonResult publish(String token,Comment comment) throws Exception {
        CommonResult commonResult = new CommonResult();
        String context=SensitiveWordFilter.sensitiveWordFilter(comment.getContext());
        comment.setContext(context);
        Claims claims = JwtUtil.parseJWT(token);
        Long userid = Long.valueOf(claims.getSubject());
        comment.setUserId(userid);
        comment.setLikes(0);
        int line =commentMapper.insert(comment);
        if (line!=1){
            commonResult.setCode(ResponseStatusCode.PUBLISH_FAIL);
            commonResult.setMessage("评论失败");
            return commonResult;
        }
        commonResult.setCode(ResponseStatusCode.SUCCESS);
        commonResult.setMessage("评论成功");
        return commonResult;
    }
}
