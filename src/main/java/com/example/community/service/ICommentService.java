package com.example.community.service;

import com.example.community.entity.Comment;
import com.example.community.entity.CommonResult;

public interface ICommentService {
    CommonResult publish(String token,Comment comment) throws Exception;
}
