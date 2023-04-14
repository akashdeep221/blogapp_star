package com.example.blogapp.articles;

import com.example.blogapp.comments.CommentEntity;
import com.example.blogapp.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Articles")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true, nullable = false)
    private String title;
    @Column(unique = true, nullable = false)
    private String slug;
    @Column
    private String subtitle;
    @Column
    private String body;
    @Column(nullable = false)
    private Date createdAt;

    @Column
    @ElementCollection
    private List<String> tags;
    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity author;
    @OneToMany(targetEntity = CommentEntity.class, cascade = CascadeType.ALL, mappedBy = "articleOfComment")
    private List<CommentEntity> commentsOfArticle;

}
