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
@Table(name = "tbl_article_evaluation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleEvaluation {
    @Id
    @Column(name = "col_article_id")
    private Integer articleId;

    @Column(name = "col_upvote")
    private Integer upVote;

    @Column(name = "col_downvote")
    private Integer downVote;
}
