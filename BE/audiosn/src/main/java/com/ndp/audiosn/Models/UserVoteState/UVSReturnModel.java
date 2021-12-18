package com.ndp.audiosn.Models.UserVoteState;

import com.ndp.audiosn.Entities.UserVoteState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UVSReturnModel {
    private Integer articleId;

    private String username;

    private Integer voteState;

    public UVSReturnModel(UserVoteState userVoteState) {
        this.articleId = userVoteState.getArticleId();

        this.username = userVoteState.getUsername();

        this.voteState = userVoteState.getVoteState();
    }
}
