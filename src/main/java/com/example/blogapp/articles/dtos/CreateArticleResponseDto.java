package com.example.blogapp.articles.dtos;

import lombok.Data;
import lombok.NonNull;

import java.util.Date;
import java.util.List;

@Data
public class CreateArticleResponseDto {
    private String title;
    private String slug;
    private String subtitle;
    private String body;
    private Date createdAt;
    private List<String> tags;
}
