package com.example.community.service.impl;

import com.example.community.entity.Article;
import com.example.community.entity.CommonResult;
import com.example.community.entity.ResponseStatusCode;
import com.example.community.mapper.ArticleMapper;
import com.example.community.service.IArticleService;
import com.example.community.utils.SensitiveWordFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements IArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public CommonResult publishArticle(Article article) {
        String title = article.getTitle();
        article.setTitle(SensitiveWordFilter.sensitiveWordFilter(title));
        String text = article.getContent();
        article.setContent(SensitiveWordFilter.sensitiveWordFilter(text));
        String flag = article.getFlag();
        article.setFlag(flag);
        int line =articleMapper.insert(article);
        CommonResult commonResult = new CommonResult();
        if (line == 1){
            commonResult.setCode(ResponseStatusCode.SUCCESS);
            commonResult.setMessage("发布成功");
            return commonResult;
        }
        commonResult.setCode(ResponseStatusCode.PUBLISH_FAIL);
        commonResult.setMessage("发布失败");
        return commonResult;
    }
}
