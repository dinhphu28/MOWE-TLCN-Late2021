package com.ndp.audiosn.Entities;

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
@Table(name = "tbl_user_vote_state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserVoteState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "col_id")
    private Integer id;

    @Column(name = "col_article_id")
    private Integer articleId;

    @Column(name = "col_username")
    private String username;

    @Column(name = "col_vote_state")
    private Integer voteState;
}
