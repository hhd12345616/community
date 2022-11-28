package com.example.community.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_comment")
public class Comment {
    @TableId
    private Long id;
    private Long articleId;
    private Long userId;
    private String context;
    private int likes;
    private boolean isAnonymity;
}
