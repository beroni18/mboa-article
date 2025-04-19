package com.sji.group9.article.servicesInterface;


import com.sji.group9.article.dto.ArticleDtoRequest;
import com.sji.group9.article.entity.Article;
import com.sji.group9.article.exception.ArticleNotFoundException;
import com.sji.group9.article.exception.ArticlePresentException;

import java.util.List;
import java.util.Optional;

public interface ArticleInterfaceService {
    Article save(ArticleDtoRequest ArticleDtoRequest) throws ArticlePresentException;
    List<Article> findByTitle(String title) throws ArticleNotFoundException;
    List<Article> findByStatut(Boolean statut);
    List<Article> findByStatutVerify(Boolean statut);
    Article update(ArticleDtoRequest ArticleDtoRequest, Long id);
    List<Article> getAll();
    Article getById(Long id);
    void deleteArticle(Long id);
}
