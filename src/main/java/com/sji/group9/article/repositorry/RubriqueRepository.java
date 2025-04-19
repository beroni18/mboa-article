package com.sji.group9.article.repositorry;

import com.sji.group9.article.entity.Rubrique;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Observed
public interface RubriqueRepository extends JpaRepository<Rubrique, Long> {
    Optional<Rubrique> findByLibelleIgnoreCase(String libelle);
}
