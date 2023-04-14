package com.example.blogapp.articles;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.blogapp.articles.dtos.*;
import com.example.blogapp.comments.CommentEntity;
import com.example.blogapp.comments.CommentRepository;
import com.example.blogapp.security.jwt.JwtService;
import com.example.blogapp.users.UserEntity;
import com.example.blogapp.users.UserRepository;
import com.example.blogapp.users.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final UserService userService;

    public ArticleService(UserRepository userRepository, ArticleRepository articleRepository, CommentRepository commentRepository, ModelMapper modelMapper, JwtService jwtService, UserService userService) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public CreateArticleResponseDto createArticle(CreateArticleDto article){
        String email;
        try {
            email = jwtService.getEmailFromJwt(article.getToken());
        }
        catch (JWTVerificationException e){
            throw new ResponseStatusException(HttpStatus.valueOf(403),"Invalid token!");
        }

    //    UserEntity userEntity = userRepository.findByEmail(email);
//        List<UserEntity> userEntityList = userRepository.findAll();
//        for (UserEntity u : userEntityList){
//            System.out.println("already having user = " + u.getUsername());
//        }


        if (userRepository.findByEmail(email)==null){
            throw new ResponseStatusException(HttpStatus.valueOf(403), "Invalid token!");
        }

        ArticleEntity newArticle = modelMapper.map(article, ArticleEntity.class);
        newArticle.setAuthor(userService.findAuthorByEmail(email));
        var savedArticle = articleRepository.save(newArticle);
        return modelMapper.map(savedArticle, CreateArticleResponseDto.class);
    }

    public CreateArticleCommentResponseDto createArticleComment(CreateArticleCommentDto commentDto){
        String email;
        try {
            email = jwtService.getEmailFromJwt(commentDto.getToken());
        }
        catch (JWTVerificationException e){
            throw new ResponseStatusException(HttpStatus.valueOf(403),"Invalid token!");
        }

        if (userRepository.findByEmail(email)==null){
            throw new ResponseStatusException(HttpStatus.valueOf(403), "Invalid token!");
        }

        CommentEntity newComment = modelMapper.map(commentDto, CommentEntity.class);
        newComment.setCommenter(userService.findAuthorByEmail(email));
        newComment.setArticleOfComment(articleRepository.findArticleByTitle(commentDto.getArticleTitle()));
        var savedComment = commentRepository.save(newComment);
        return modelMapper.map(savedComment, CreateArticleCommentResponseDto.class);
    }

    public GetArticlesDto getArticleBySlug(String slug){
        ArticleEntity article = articleRepository.findArticleBySlug(slug);

        if (article==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found");
        }

        return modelMapper.map(article, GetArticlesDto.class);
    }

    public GetArticleCommentsDto getArticleCommentsBySlug(String slug){
        ArticleEntity article = articleRepository.findArticleBySlug(slug);

        if (article==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found");
        }

        List<CommentEntity> commentsOfArticle = article.getCommentsOfArticle();
        return modelMapper.map(commentsOfArticle, GetArticleCommentsDto.class);
    }

    public List<CreateArticleResponseDto> mapList(List<ArticleEntity> source,
                                                  Class<CreateArticleResponseDto> targetClass){
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
    public GetArticlesDto getArticles(String tag, String authorName, String page, int size){

        try {
            if (tag != null && authorName == null && page == null) {
                List<ArticleEntity> listOfArticles = articleRepository.findByTag(tag);

                return modelMapper.map(listOfArticles, GetArticlesDto.class);
            }
            else if (tag == null && authorName != null && page == null) {
                UserEntity author = userRepository.findByUsername(authorName);
                List<ArticleEntity> listOfArticles = author.getArticlesByUser();
                return modelMapper.map(listOfArticles, GetArticlesDto.class);
            }
            else if (tag == null && authorName == null && page != null) {
                List<ArticleEntity> listOfArticles = articleRepository.findAll();
                if (listOfArticles.size() > size) {
                    List<ArticleEntity> newList = listOfArticles.subList(Integer.parseInt(page) * size,
                            Integer.parseInt(page) * size + size);
                    return modelMapper.map(newList, GetArticlesDto.class);
                }
                else {
                    return modelMapper.map(listOfArticles, GetArticlesDto.class);
                }
            }
            else {
                List<ArticleEntity> listOfArticles = articleRepository.findAll();
                for (ArticleEntity article : listOfArticles){
                    System.out.println(article.getTitle());
                }
                return modelMapper.map(listOfArticles, GetArticlesDto.class);
            }
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please look into the request");
        }
    }

    public void deleteArticleComment(String articleSlug, Long id){
        ArticleEntity article = articleRepository.findArticleBySlug(articleSlug);

        if (article==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found");
        }

        List<CommentEntity> commentsOfArticle = article.getCommentsOfArticle();
        for (CommentEntity comment : commentsOfArticle){
            if (comment.getId().equals(id)){
                commentRepository.delete(comment);
                break;
            }
        }
    }

    public PatchArticleResponseDto patchArticle(String slug, PatchArticleDto request){
        String email;
        try {
            email = jwtService.getEmailFromJwt(request.getToken());
        }
        catch (JWTVerificationException e){
            throw new RuntimeException("Invalid token!");
        }
        ArticleEntity foundArticle = articleRepository.findArticleBySlug(slug);
        modelMapper.map(request, foundArticle);
        return modelMapper.map(foundArticle, PatchArticleResponseDto.class);
    }
}
