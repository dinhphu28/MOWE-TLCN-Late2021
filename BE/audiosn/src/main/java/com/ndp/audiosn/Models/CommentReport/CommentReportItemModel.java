package com.ndp.audiosn.Models.CommentReport;

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
public class CommentReportItemModel {
    private Integer id;

    private Integer commentId;

    // private Integer articleId;

    private String author;

    private LocalDate date;

    private LocalTime time;

    private String content;

    private String commentAuthor;

    private String commentContent;

    private String articleTitle;

    private String articleUrl;

    private Boolean solved;
}
