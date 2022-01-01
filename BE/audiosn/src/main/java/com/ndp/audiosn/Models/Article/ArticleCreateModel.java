package com.ndp.audiosn.Models.Article;

import java.time.LocalDate;
import java.time.LocalTime;

import com.ndp.audiosn.Entities.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreateModel {
    private String title;

    private String description;

    private String content;

    private String audioContent;

    // private LocalDate dateCreated;

    // private LocalTime timeCreated;

    private String author;

    // private String url;

    private String category;

    private String thumbnailUrl;

    public Article toArticle(LocalDate dateCreated, LocalTime timeCreated, String url) {
        // Article article = new Article(0, title, description, content, dateCreated, timeCreated, author, url, category, thumbnailUrl);

        Article article = new Article(0, title, description, content, audioContent, dateCreated, timeCreated, author, url, category, thumbnailUrl, false);

        return article;
    }
}
