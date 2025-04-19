package com.sji.group9.article.servicesImpl;


import com.sji.group9.article.dto.ArticleDtoRequest;
import com.sji.group9.article.entity.Article;
import com.sji.group9.article.exception.ArticleNotFoundException;
import com.sji.group9.article.exception.ArticlePresentException;
import com.sji.group9.article.repositorry.ArticleRepository;
import com.sji.group9.article.repositorry.RubriqueRepository;
import com.sji.group9.article.servicesInterface.ArticleInterfaceService;

import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Observed
public class ArticleServices implements ArticleInterfaceService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private RubriqueRepository rubriqueRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Override
    public Article save(ArticleDtoRequest articleDtoRequest) throws ArticlePresentException {
        Article newArticle = articleDtoRequest.mapToArticle();

        if (articleRepository.findByTitre(articleDtoRequest.getTitre()).isEmpty()) {
            newArticle.setRubrique(rubriqueRepository.findById(articleDtoRequest.getId_rubrique()).get());
            newArticle = articleRepository.save(newArticle);
            //Kafka
//            ArticleAddEvent ArticleAddEvent = new ArticleAddEvent(newArticle.getArticlename(), newArticle.getEmail(), uncryptedPwd);
//            System.out.println("Start - Sending ArticleAddEvent {} to Kafka topic add-Article");
//            kafkaTemplate.send("Article-add", ArticleAddEvent);
//            System.out.println("End - Sending ArticleAddEven {} to Kafka topic Article-add");
            //kafka
            return newArticle;
        }
        else {
            throw new ArticlePresentException("Article with title: " + articleDtoRequest.getTitre() + " already exist");
        }
    }

    @Override
    public List<Article> findByTitle(String title) throws ArticleNotFoundException {
        List<Article> articles = articleRepository.findByTitre(title);
        if (!articles.isEmpty()){
            return articles;
        }
        else {
            throw new ArticleNotFoundException("Aucun article trouve");
        }
    }

    @Override
    public List<Article> findByStatut(Boolean statut) {
        return articleRepository.findByStatut(statut);
    }

    @Override
    public List<Article> findByStatutVerify(Boolean statut) {
        return articleRepository.findByIsVerify(statut);
    }

    @Override
    public Article update(ArticleDtoRequest articleDtoRequest, Long id) {
        Article newArticle = articleDtoRequest.mapToArticle();
        newArticle.setId(id);
        newArticle.setRubrique(rubriqueRepository.findById(articleDtoRequest.getId_rubrique()).get());
        return articleRepository.save(newArticle);
    }

    @Override
    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article getById(Long id) {
        return articleRepository.findById(id).get();
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

}
