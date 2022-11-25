package com.example.community.service;

import com.example.community.entity.Article;
import com.example.community.entity.CommonResult;

public interface IArticleService {
    CommonResult publishArticle(Article article);
}
