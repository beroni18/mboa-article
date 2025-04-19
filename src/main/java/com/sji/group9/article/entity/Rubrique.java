package com.sji.group9.article.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
public class Rubrique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String libelle;

    @OneToMany(mappedBy = "rubrique") // Le "mappedBy" fait référence au champ "rubrique" dans PointInteret
    @JsonIgnore
    private List<Article> articles;

}
