package com.example.community.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value="sys_article")
public class Article {
    @TableId
    private Long id;
    private Long user_id;
    private String title;
    private String content;
    private String flag;
    private int likes;
    private boolean isAccess;
}
