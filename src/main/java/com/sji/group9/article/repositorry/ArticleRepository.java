package com.sji.group9.article.repositorry;

import com.sji.group9.article.entity.Article;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Observed
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTitre(String titre);
    List<Article> findByStatut(Boolean statut);
    List<Article> findByIsVerify(Boolean is_verify);
    List<Article> findByRubrique_Libelle(String libelle_rubrique);
    List<Article> findByEmailUser(String email);
}
