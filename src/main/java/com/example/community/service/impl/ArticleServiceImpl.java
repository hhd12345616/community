package com.example.community.service.impl;

import com.example.community.entity.Article;
import com.example.community.entity.CommonResult;
import com.example.community.entity.ResponseStatusCode;
import com.example.community.mapper.ArticleMapper;
import com.example.community.service.IArticleService;
import com.example.community.utils.JwtUtil;
import com.example.community.utils.SensitiveWordFilter;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements IArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public CommonResult publishArticle(String token,Article article) throws Exception {
        String title = article.getTitle();
        article.setTitle(SensitiveWordFilter.sensitiveWordFilter(title));
        String text = article.getContent();
        article.setContent(SensitiveWordFilter.sensitiveWordFilter(text));
        String flag = article.getFlag();
        article.setFlag(flag);
        article.setLikes(0);
        article.setAccess(false);
        Claims claims = JwtUtil.parseJWT(token);
        String user_id = claims.getSubject();
        article.setUser_id(Long.valueOf(user_id));
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

    @Override
    public CommonResult queryByPage(Integer page, Integer size) {
        CommonResult commonResult= new CommonResult();
        List<Article> articles = articleMapper.selectByPage(page,size);
        if (articles==null||articles.size()==0){
            commonResult.setCode(ResponseStatusCode.QUERY_FAIL);
            commonResult.setMessage("未查询到结果");
            return commonResult;
        }
        commonResult.setCode(ResponseStatusCode.SUCCESS);
        commonResult.setMessage("查询成功");
        commonResult.setData(articles);
        return commonResult;
    }
}
