package com.example.blogapp.comments;

import com.example.blogapp.articles.ArticleEntity;
import com.example.blogapp.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true, nullable = false)
    private String title;
    @Column
    private String body;
    @Column(nullable = false)
    private Date createdAt;
    @Column(nullable = false)
    private String articleTitle;
    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity commenter;
    @ManyToOne(targetEntity = ArticleEntity.class)
    private ArticleEntity articleOfComment;
}
