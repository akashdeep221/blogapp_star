package com.example.blogapp.articles;

import com.example.blogapp.articles.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("")
    public ResponseEntity<CreateArticleResponseDto> createArticle(
            @RequestBody CreateArticleDto request
    ) {
        var createdArticle = articleService.createArticle(request);
        return ResponseEntity.created(URI.create("/articles/" + createdArticle.getTitle())).body(createdArticle);
    }

    @PostMapping("/comments")
    public ResponseEntity<CreateArticleCommentResponseDto> createArticleComment(
            @RequestBody CreateArticleCommentDto request
    ) {
        var createdArticleComment = articleService.createArticleComment(request);
        return ResponseEntity.created(URI.create("/articles/comments" +
                createdArticleComment.getTitle())).body(createdArticleComment);
    }

    @GetMapping("")
    public ResponseEntity<GetArticlesDto> getArticles(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "page", required = false) String page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ){
        return ResponseEntity.ok(articleService.getArticles(tag, author, page, size));
    }

    @GetMapping("/{article-slug}")
    public ResponseEntity<GetArticlesDto> getArticle(@PathVariable("article-slug") String slug) {
        return ResponseEntity.ok(articleService.getArticleBySlug(slug));            // can add author name
    }

    @GetMapping("/{article-slug}/comments")
    public ResponseEntity<GetArticleCommentsDto> getArticleComments(@PathVariable("article-slug") String slug) {
        return ResponseEntity.ok(articleService.getArticleCommentsBySlug(slug));
    }

    @PatchMapping("/{article-slug}")
    public ResponseEntity<PatchArticleResponseDto> patchArticle(@PathVariable("article-slug") String slug,
                                                        @RequestBody PatchArticleDto request){
        var patchedArticle = articleService.patchArticle(slug,request);
        return ResponseEntity.created(URI.create("/articles/{article-slug}" +
                patchedArticle.getTitle())).body(patchedArticle);
    }

    @DeleteMapping("/article/{article-slug}/comments/{comment-id}")
    public ResponseEntity<GetArticleCommentsDto> deleteArticleComment(
            @PathVariable("article-slug") String articleSlug,
            @PathVariable("comment-id") Long id
    ){
        articleService.deleteArticleComment(articleSlug, id);
        return ResponseEntity.ok(articleService.getArticleCommentsBySlug(articleSlug));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException e){
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }

}
