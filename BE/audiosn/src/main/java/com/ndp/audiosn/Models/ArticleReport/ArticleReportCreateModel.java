package com.ndp.audiosn.Models.ArticleReport;

import java.time.LocalDate;
import java.time.LocalTime;

import com.ndp.audiosn.Entities.ArticleReport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleReportCreateModel {
    private Integer articleId;

    private String author;

    private String content;

    public ArticleReport toArticleReport(LocalDate date, LocalTime time) {
        return new ArticleReport(0, articleId, author, date, time, content, false);
    }
}
