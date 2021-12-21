package com.ndp.audiosn.Models.ArticleReport;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleReportItemModel {
    private Integer id;

    private Integer articleId;

    private String author;

    private LocalDate date;

    private LocalTime time;

    private String content;

    private String articleTitle;

    private String articleUrl;
}
