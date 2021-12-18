package com.ndp.audiosn.JpaRepo;

import com.ndp.audiosn.Entities.ArticleInteraction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleInteractionRepo extends JpaRepository<ArticleInteraction, Integer> {
    
}
