package com.example.blogapp.articles;

import com.example.blogapp.articles.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

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
        return ResponseEntity.ok(articleService.getArticles(tag,author,page,size));
    }

    @GetMapping("/{article-slug}")
    public ResponseEntity<CreateArticleResponseDto> getArticle(@PathVariable("article-slug") String slug) {
        return ResponseEntity.ok(articleService.getArticleBySlug(slug));            // can add author name
    }

    @GetMapping("/{article-slug}/comments")
    public ResponseEntity<GetArticleCommentsDto> getArticleComments(@PathVariable("article-slug") String slug) {
        return ResponseEntity.ok(articleService.getArticleCommentsBySlug(slug));
    }

    @PatchMapping("")
    public ResponseEntity<PatchArticleResponseDto> patchArticle(@RequestBody PatchArticleDto request){
        var patchedArticle = articleService.patchArticle(request);
        return ResponseEntity.created(URI.create("/articles" +
                patchedArticle.getTitle())).body(patchedArticle);       // todo token fix
    }

//    @DeleteMapping("/article/comments")
//    public ResponseEntity<GetArticleCommentsDto> deleteArticleComment(
//            @RequestParam(value = "articleSlug", required = true) String tag,
//            @RequestParam(value = "", required = true) String author
//    ){
//        articleService.deleteArticleComment(articleSlug, id);
//        return ResponseEntity.ok(articleService.getArticleCommentsBySlug(articleSlug));
//    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException e){
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }

}
