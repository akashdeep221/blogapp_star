package com.example.blogapp.articles.dtos;

import com.example.blogapp.comments.CommentEntity;
import lombok.Data;

import java.util.List;

@Data
public class GetArticleCommentsDto {
    private List<CommentEntity> commentsOfArticle;
}
