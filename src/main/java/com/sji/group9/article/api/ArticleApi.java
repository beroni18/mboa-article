package com.sji.group9.article.api;

import com.sji.group9.article.dto.ArticleDtoRequest;
import com.sji.group9.article.entity.Article;
import com.sji.group9.article.exception.ArticleNotFoundException;
import com.sji.group9.article.exception.ArticlePresentException;
import com.sji.group9.article.servicesImpl.ArticleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleApi {

    @Autowired
    private ArticleServices articleService;

    // Créer un nouvel article
    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody ArticleDtoRequest articleDtoRequest) {
        try {
            Article savedArticle = articleService.save(articleDtoRequest);
            return new ResponseEntity<>(savedArticle, HttpStatus.CREATED);
        } catch (ArticlePresentException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    // Récupérer un article par son titre
    @GetMapping("/title/{title}")
    public ResponseEntity<List<Article>> getArticlesByTitle(@PathVariable String title) {
        try {
            List<Article> articles = articleService.findByTitle(title);
            return new ResponseEntity<>(articles, HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Récupérer les articles par statut
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Article>> getArticlesByStatut(@PathVariable Boolean statut) {
        List<Article> articles = articleService.findByStatut(statut);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    // Récupérer les articles par statut de vérification
    @GetMapping("/verify/{statut}")
    public ResponseEntity<List<Article>> getArticlesByVerifyStatut(@PathVariable Boolean statut) {
        List<Article> articles = articleService.findByStatutVerify(statut);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    // Mettre à jour un article
    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody ArticleDtoRequest articleDtoRequest) {
        Article updatedArticle = articleService.update(articleDtoRequest, id);
        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    }

    // Récupérer tous les articles
    @GetMapping("/all")
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = articleService.getAll();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    // Récupérer un article par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        Article article = articleService.getById(id);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    // Supprimer un article
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
