package com.example.community;

import com.example.community.entity.Article;
import com.example.community.entity.User;
import com.example.community.mapper.ArticleMapper;
import com.example.community.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class CommunityApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    void contextLoads() {
        User user= userMapper.selectOne(null);
        System.out.println(user);
        PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("123")+"\t"+passwordEncoder.matches("123",user.getPassword()));
    }
    @Test
    void testArticle(){
        Article article = new Article();
        article.setTitle("test");
        article.setContent("1111111111111111111111111111111");
        article.setFlag("[test]");
        articleMapper.insert(article);
    }

}
