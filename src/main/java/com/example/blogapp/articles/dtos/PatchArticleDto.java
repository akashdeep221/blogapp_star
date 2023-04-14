package com.example.blogapp.articles.dtos;

import lombok.Data;
import lombok.NonNull;

import java.util.Date;
import java.util.List;

@Data
public class PatchArticleDto {
    @NonNull
    String title;
    @NonNull
    String slug;
    String subtitle;
    String body;
    @NonNull
    Date createdAt;
    List<String> tags;
    @NonNull
    String token;
}
