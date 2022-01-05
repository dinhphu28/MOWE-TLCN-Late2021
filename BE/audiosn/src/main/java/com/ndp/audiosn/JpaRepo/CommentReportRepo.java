package com.ndp.audiosn.JpaRepo;

import java.util.List;

import com.ndp.audiosn.Entities.CommentReport;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReportRepo extends JpaRepository<CommentReport, Integer> {
    List<CommentReport> findByCommentId(Integer commentId);

    List<CommentReport> findByCommentIdOrderByIdDesc(Integer commentId);

    List<CommentReport> findByOrderByIdDesc();

    Long deleteByCommentId(Integer commentId);

    List<CommentReport> findBySolved(Boolean solved);
}
