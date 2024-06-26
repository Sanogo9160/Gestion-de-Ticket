package com.gestionticket.project.repository;

import com.gestionticket.project.model.KnowledgeArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnowledgeArticleRepository extends JpaRepository<KnowledgeArticle, Long> {
    List<KnowledgeArticle> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String keyword, String keyword1);
    // méthodes spécifiques du repository si nécessaire
}
