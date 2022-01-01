package com.ndp.audiosn.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_article_interaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleInteraction {
    @Id
    @Column(name = "col_article_id")
    private Integer id;

    @Column(name = "col_comment_score")
    private Integer commentScore;

    @Column(name = "col_vote_score")
    private Integer voteScore;
}
