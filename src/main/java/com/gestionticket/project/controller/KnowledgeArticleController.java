package com.gestionticket.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gestionticket.project.model.KnowledgeArticle;
import com.gestionticket.project.service.KnowledgeArticleService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/articles")
@AllArgsConstructor
public class KnowledgeArticleController {

    private final KnowledgeArticleService articleService;

    @GetMapping
    public List<KnowledgeArticle> getAllArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<KnowledgeArticle> getArticleById(@PathVariable("id") Long id) {
        Optional<KnowledgeArticle> article = articleService.getArticleById(id);
        return article.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<KnowledgeArticle> createArticle(@RequestBody KnowledgeArticle article) {
        KnowledgeArticle createdArticle = articleService.createArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdArticle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KnowledgeArticle> updateArticle(@PathVariable("id") Long id, @RequestBody KnowledgeArticle article) {
        KnowledgeArticle updatedArticle = articleService.updateArticle(id, article);
        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<KnowledgeArticle> searchArticles(@RequestParam("keyword") String keyword) {
        return articleService.searchArticles(keyword);
    }
}
