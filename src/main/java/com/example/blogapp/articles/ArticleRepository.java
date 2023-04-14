package com.example.blogapp.articles;

import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    ArticleEntity findArticleByTitle(String articleTitle);

    ArticleEntity findArticleBySlug(String slug);

    @Query("SELECT a FROM Articles a WHERE :tagName MEMBER OF a.tags")
    List<ArticleEntity> findByTag(String tagName);

    List<ArticleEntity> findAll();
}
