package com.example.community.controller;

import com.example.community.entity.Comment;
import com.example.community.entity.CommonResult;
import com.example.community.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    @Autowired
    private ICommentService service;

    @PostMapping("/comment/publish")
    public CommonResult comment(@RequestHeader String token, @RequestParam Long articleId, @RequestBody Comment comment) throws Exception {
        comment.setArticleId(articleId);
        return service.publish(token,comment);
    }
}
