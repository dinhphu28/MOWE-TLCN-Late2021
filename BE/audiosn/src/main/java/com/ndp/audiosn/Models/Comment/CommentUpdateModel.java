package com.ndp.audiosn.Models.Comment;

import java.time.LocalDate;
import java.time.LocalTime;

import com.ndp.audiosn.Entities.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateModel {
    private String author;

    // private LocalDate date;

    // private LocalTime time;

    private String content;

    private String audioContent;

    public Comment toComment(Integer articleId, Integer id, LocalDate date, LocalTime time) {
        // Comment comment = new Comment(id, author, articleId, date, time, content);

        Comment comment = new Comment(0, author, articleId, date, time, content, audioContent);

        return comment;
    }
}
