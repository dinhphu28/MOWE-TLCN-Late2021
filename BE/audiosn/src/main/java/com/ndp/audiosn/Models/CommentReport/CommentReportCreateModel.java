package com.ndp.audiosn.Models.CommentReport;

import java.time.LocalDate;
import java.time.LocalTime;

import com.ndp.audiosn.Entities.CommentReport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentReportCreateModel {
    private Integer commentId;

    private String author;

    private String content;

    public CommentReport toCommentReport(LocalDate date, LocalTime time) {
        return new CommentReport(0, commentId, author, date, time, content, false);
    }
}
