package com.ndp.audiosn.Entities;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_comment_report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "col_id")
    private Integer id;

    @Column(name = "col_comment_id")
    private Integer commentId;

    @Column(name = "col_author")
    private String author;

    @Column(name = "col_date")
    private LocalDate date;

    @Column(name = "col_time")
    private LocalTime time;

    @Column(name = "col_content")
    private String content;

    @Column(name = "col_solved")
    private Boolean solved;
}
