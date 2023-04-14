package com.example.blogapp.articles.dtos;

import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class CreateArticleCommentDto {
    @NonNull
    String title;
    String body;
    @NonNull
    Date createdAt;
    @NonNull
    String articleTitle;
    @NonNull
    String token;
}
