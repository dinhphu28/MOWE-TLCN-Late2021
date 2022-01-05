package com.ndp.audiosn.Services;

import java.util.List;

import com.ndp.audiosn.Entities.Comment;
import com.ndp.audiosn.JpaRepo.CommentRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {
    @Autowired
    private CommentRepo repo;

    public List<Comment> retrieveAll() {
        return repo.findAll();
    }

    public List<Comment> retrieveAllByArticleId(Integer articleId) {
        return repo.findByArticleId(articleId);
    }

    public List<Comment> retrieveAllByArticleIdAndHidden(Integer articleId, Boolean hidden) {
        return repo.findByArticleIdAndHidden(articleId, hidden);
    }

    public Comment retrieveOne(Integer id) {
        Comment sth = null;

        try {
            sth = repo.findById(id).get();
        } catch (Exception e) {
            //TODO: handle exception
        }

        return sth;
    }

    public Comment createOne(Comment comment) {
        Comment tmpComment = null;

        comment.setId(0);

        try {
            tmpComment = repo.save(comment);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpComment;
    }

    public Comment updateOne(Comment comment) {
        Comment tmpComment = null;

        try {
            repo.findById(comment.getId()).get();

            tmpComment = repo.save(comment);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpComment;
    }

    public Boolean deleteOne(Integer id) {
        Boolean kk = false;

        try {
            repo.findById(id).get();
            
            repo.deleteById(id);

            kk = true;
        } catch (Exception e) {
            //TODO: handle exception
        }

        return kk;
    }

    public Boolean deleteAllByArticleId(Integer articleId) {
        Boolean kk = false;

        try {
            Long ii = repo.deleteByArticleId(articleId);

            if(ii > 0) {
                kk = true;
            }
        } catch (Exception e) {
            //TODO: handle exception
        }

        return kk;
    }
}
