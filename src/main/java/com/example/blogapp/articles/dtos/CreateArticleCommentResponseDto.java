package com.example.blogapp.articles.dtos;

import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class CreateArticleCommentResponseDto {

    private String title;
    private String body;
    private Date createdAt;
    private String articleTitle;
}
