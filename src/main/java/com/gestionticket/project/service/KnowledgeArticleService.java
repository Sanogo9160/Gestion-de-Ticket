package com.gestionticket.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestionticket.project.exception.ResourceNotFoundException;
import com.gestionticket.project.model.KnowledgeArticle;
import com.gestionticket.project.repository.KnowledgeArticleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class KnowledgeArticleService {

    private final KnowledgeArticleRepository articleRepository;

    public List<KnowledgeArticle> getAllArticles() {
        return articleRepository.findAll();
    }

    public Optional<KnowledgeArticle> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public KnowledgeArticle createArticle(KnowledgeArticle article) {
        return articleRepository.save(article);
    }

    public KnowledgeArticle updateArticle(Long id, KnowledgeArticle articleDetails) {
        Optional<KnowledgeArticle> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isPresent()) {
            KnowledgeArticle existingArticle = optionalArticle.get();
            existingArticle.setTitle(articleDetails.getTitle());
            existingArticle.setContent(articleDetails.getContent());
            existingArticle.setCategory(articleDetails.getCategory());
            return articleRepository.save(existingArticle);
        } else {
            throw new ResourceNotFoundException("Article not found with id: " + id);
        }
    }

    public void deleteArticle(Long id) {
        Optional<KnowledgeArticle> article = articleRepository.findById(id);
        article.ifPresent(articleRepository::delete);
    }

    public List<KnowledgeArticle> searchArticles(String keyword) {
        return articleRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);
    }
}
