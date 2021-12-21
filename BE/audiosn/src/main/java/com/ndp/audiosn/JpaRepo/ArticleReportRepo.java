package com.ndp.audiosn.JpaRepo;

import java.util.List;

import com.ndp.audiosn.Entities.ArticleReport;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleReportRepo extends JpaRepository<ArticleReport, Integer> {
    List<ArticleReport> findByArticleId(Integer articleId);

    List<ArticleReport> findByArticleIdOrderByIdDesc(Integer articleId);

    List<ArticleReport> findByOrderByIdDesc();

    Long deleteByArticleId(Integer articleId);
}
