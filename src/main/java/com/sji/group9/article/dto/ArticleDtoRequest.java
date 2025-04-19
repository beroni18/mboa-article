package com.sji.group9.article.dto;

import com.sji.group9.article.entity.Article;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ArticleDtoRequest {
    private String titre;
    private String description;
    private Boolean statut;
    private Boolean isVerify;
    @Lob
    private String photo;
    private String emailUser;
    private Long id_rubrique;

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatut() {
        return statut;
    }

    public void setStatut(Boolean statut) {
        this.statut = statut;
    }

    public Boolean getVerify() {
        return isVerify;
    }

    public void setVerify(Boolean verify) {
        isVerify = verify;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public Long getId_rubrique() {
        return id_rubrique;
    }

    public void setId_rubrique(Long id_rubrique) {
        this.id_rubrique = id_rubrique;
    }

    public Article mapToArticle(){
        Article article = new Article();
        article.setTitre(titre);
        article.setDescription(description);
        article.setStatut(statut);
        article.setPhoto(photo);
        article.setEmailUser(emailUser);
        return article;
    }
}
