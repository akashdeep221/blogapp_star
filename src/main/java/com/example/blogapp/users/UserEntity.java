package com.example.blogapp.users;

import com.example.blogapp.articles.ArticleEntity;
import com.example.blogapp.comments.CommentEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Users")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String bio;
    private String imageLink;
    @OneToMany(targetEntity = CommentEntity.class, cascade = CascadeType.ALL, mappedBy = "commenter")
    private List<CommentEntity> commentsByUser;
    @OneToMany(targetEntity = ArticleEntity.class, cascade = CascadeType.ALL, mappedBy = "author")
    private List<ArticleEntity> articlesByUser;

}
