package com.example.community.controller;

import com.example.community.entity.Article;
import com.example.community.entity.CommonResult;
import com.example.community.service.IArticleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {
    @Autowired
    private IArticleService service;

    @PostMapping("/article/publish")
    @PreAuthorize("hasAuthority('system:article:publish')")
    public CommonResult publishArticle(@RequestHeader String token,@RequestBody Article article) throws Exception {
        return service.publishArticle(token,article);
    }

    @GetMapping("/article/queryByPage")
    @PreAuthorize("hasAuthority('system:article:publish')")
    public CommonResult queryByPage(@RequestParam Integer page,@RequestParam Integer size){
        return service.queryByPage(page-1,size);
    }
}
