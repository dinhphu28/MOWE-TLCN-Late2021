package com.ndp.audiosn.JpaRepo;

import java.util.List;

import com.ndp.audiosn.Entities.UserVoteState;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVoteStateRepo extends JpaRepository<UserVoteState, Integer> {
    List<UserVoteState> findByArticleId(Integer articleId);

    UserVoteState findByArticleIdAndUsername(Integer articleId, String username);

    Long countByArticleId(Integer articleId);

    Long deleteByArticleId(Integer articleId);
}
