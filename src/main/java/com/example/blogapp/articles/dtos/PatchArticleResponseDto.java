package com.example.blogapp.articles.dtos;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PatchArticleResponseDto {
    private String title;
    private String slug;
    private String subtitle;
    private String body;
    private Date createdAt;
    private List<String> tags;
}
