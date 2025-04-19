package com.sji.group9.article.servicesImpl;

import com.sji.group9.article.dto.ArticleDtoRequest;
import com.sji.group9.article.entity.Article;
import com.sji.group9.article.entity.Rubrique;
import com.sji.group9.article.exception.ArticleNotFoundException;
import com.sji.group9.article.exception.ArticlePresentException;
import com.sji.group9.article.repositorry.ArticleRepository;
import com.sji.group9.article.repositorry.RubriqueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticleServicesTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private RubriqueRepository rubriqueRepository;

    @InjectMocks
    private ArticleServices articleServices;

    private ArticleDtoRequest articleDtoRequest;
    private Article article;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        articleDtoRequest = new ArticleDtoRequest();
        articleDtoRequest.setTitre("Test Article");
        articleDtoRequest.setId_rubrique(1L);
        article = new Article();
        article.setId(1L);
        article.setTitre("Test Article");
    }

    @Test
    void testSave_Success() throws ArticlePresentException {
        when(articleRepository.findByTitre(articleDtoRequest.getTitre())).thenReturn(new ArrayList<>()); // Retourner une liste vide
        when(rubriqueRepository.findById(articleDtoRequest.getId_rubrique())).thenReturn(Optional.of(new Rubrique()));
        when(articleRepository.save(any(Article.class))).thenReturn(article);

        Article savedArticle = articleServices.save(articleDtoRequest);

        assertNotNull(savedArticle);
        assertEquals("Test Article", savedArticle.getTitre());
        verify(articleRepository, times(1)).save(any(Article.class));
    }

    @Test
    void testSave_ArticleAlreadyExists() {
        when(articleRepository.findByTitre(articleDtoRequest.getTitre())).thenReturn(List.of(article)); // Simulez un article existant

        Exception exception = assertThrows(ArticlePresentException.class, () -> {
            articleServices.save(articleDtoRequest);
        });

        assertEquals("Article with title: Test Article already exist", exception.getMessage());
    }

    @Test
    void testFindByTitle_Success() throws ArticleNotFoundException {
        List<Article> articles = new ArrayList<>();
        articles.add(article);
        when(articleRepository.findByTitre(article.getTitre())).thenReturn(articles);

        List<Article> foundArticles = articleServices.findByTitle(article.getTitre());

        assertNotNull(foundArticles);
        assertEquals(1, foundArticles.size());
        assertEquals("Test Article", foundArticles.get(0).getTitre());
    }

    @Test
    void testFindByTitle_NotFound() {
        when(articleRepository.findByTitre(article.getTitre())).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(ArticleNotFoundException.class, () -> {
            articleServices.findByTitle(article.getTitre());
        });

        assertEquals("Aucun article trouve", exception.getMessage());
    }

    @Test
    void testGetAll() {
        List<Article> articles = new ArrayList<>();
        articles.add(article);
        when(articleRepository.findAll()).thenReturn(articles);

        List<Article> foundArticles = articleServices.getAll();

        assertNotNull(foundArticles);
        assertEquals(1, foundArticles.size());
    }

    @Test
    void testGetById_Success() {
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        Article foundArticle = articleServices.getById(1L);

        assertNotNull(foundArticle);
        assertEquals("Test Article", foundArticle.getTitre());
    }

    @Test
    void testGetById_NotFound() {
        when(articleRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            articleServices.getById(1L);
        });

        assertEquals("No value present", exception.getMessage());
    }

    @Test
    void testDeleteArticle_Success() {
        doNothing().when(articleRepository).deleteById(1L);

        assertDoesNotThrow(() -> articleServices.deleteArticle(1L));
        verify(articleRepository, times(1)).deleteById(1L);
    }
}