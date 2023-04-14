package com.example.blogapp.articles.dtos;

import com.example.blogapp.articles.ArticleEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GetArticlesDto {
    private List<ArticleEntity> articleEntities;
}
