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
public class ArticleUpdateModel {
    private String title;

    private String description;

    private String content;

    private String audioContent;

    // private LocalDate dateCreated;

    // private LocalTime timeCreated;

    private String userAgent;

    // private String url;

    private String category;

    private String thumbnailUrl;

    public Article toArticle(Integer id, LocalDate dateCreated, LocalTime timeCreated, String author, String url) {
        // Article article = new Article(id, title, description, content, dateCreated, timeCreated, author, url, category, thumbnailUrl);

        Article article = new Article(id, title, description, content, audioContent, dateCreated, timeCreated, author, url, category, thumbnailUrl);

        return article;
    }
}
