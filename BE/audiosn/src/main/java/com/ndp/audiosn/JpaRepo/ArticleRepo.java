package com.ndp.audiosn.JpaRepo;

import java.util.List;

import com.ndp.audiosn.Entities.Article;

import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Integer> {
    List<Article> findByCategory(String category, Pageable pageable);

    List<Article> findByCategoryAndHidden(String category, Boolean hidden, Pageable pageable);

    List<Article> findByHidden(Boolean hidden, Pageable pageable);

    Long countByCategory(String category);

    Long countByCategoryAndHidden(String category, Boolean hidden);

    Long countByHidden(Boolean hidden);
}
