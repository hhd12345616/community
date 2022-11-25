package com.example.community.controller;

import com.example.community.entity.Article;
import com.example.community.entity.CommonResult;
import com.example.community.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {
    @Autowired
    private IArticleService service;

    @PostMapping("/article/publish")
    @PreAuthorize("hasAuthority('system:article:publish')")
    public CommonResult publishArticle(@RequestBody Article article){
        return service.publishArticle(article);
    }


}
