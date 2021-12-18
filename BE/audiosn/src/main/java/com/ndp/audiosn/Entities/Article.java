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
@Table(name = "tbl_article")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "col_id")
    private Integer id;

    @Column(name = "col_title")
    private String title;

    @Column(name = "col_description")
    private String description;

    @Column(name = "col_content")
    private String content;

    @Column(name = "col_audio_content")
    private String audioContent;

    @Column(name = "col_date_created")
    private LocalDate dateCreated;

    @Column(name = "col_time_created")
    private LocalTime timeCreated;

    @Column(name = "col_author")
    private String author;

    @Column(name = "col_url")
    private String url;

    @Column(name = "col_category")
    private String category;

    @Column(name = "col_thumbnail_url")
    private String thumbnailUrl;
}
