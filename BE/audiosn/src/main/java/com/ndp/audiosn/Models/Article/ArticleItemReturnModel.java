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
public class ArticleItemReturnModel {
    private Integer id;

    private String title;

    private String description;

    private String content;

    private String audioContent;

    private LocalDate dateCreated;

    private LocalTime timeCreated;

    private String author;

    private String url;

    private String category;

    private String thumbnailUrl;

    private Integer voteScore;

    public ArticleItemReturnModel(Article article, Integer voteScore) {
        this.id = article.getId();

        this.title = article.getTitle();

        this.description = article.getDescription();

        this.content = article.getContent();

        this.audioContent = article.getAudioContent();

        this.dateCreated = article.getDateCreated();

        this.timeCreated = article.getTimeCreated();

        this.author = article.getAuthor();

        this.url = article.getUrl();

        this.category = article.getCategory();

        this.thumbnailUrl = article.getThumbnailUrl();

        this.voteScore = voteScore;
    }
}
