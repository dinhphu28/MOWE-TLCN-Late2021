package com.ndp.audiosn.JpaRepo;

import com.ndp.audiosn.Entities.ArticleEvaluation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleEvaluationRepo extends JpaRepository<ArticleEvaluation, Integer> {
    
}
