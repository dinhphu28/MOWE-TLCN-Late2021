package com.ndp.audiosn.Services;

import java.util.List;

import com.ndp.audiosn.Entities.CommentReport;
import com.ndp.audiosn.JpaRepo.CommentReportRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentReportService {
    @Autowired
    private CommentReportRepo repo;

    public List<CommentReport> retrieveAll() {
        // return repo.findAll();
        return repo.findByOrderByIdDesc();
    }

    public List<CommentReport> retrieveAllByCommentId(Integer commentId) {
        // return repo.findByCommentId(CommentId);
        return repo.findByCommentIdOrderByIdDesc(commentId);
    }

    public List<CommentReport> retrieveBySolved(Boolean solved) {
        return repo.findBySolved(solved);
    }

    public CommentReport retrieveOne(Integer id) {
        CommentReport sth = null;

        try {
            sth = repo.findById(id).get();
        } catch (Exception e) {
            //TODO: handle exception
        }

        return sth;
    }

    public CommentReport createOne(CommentReport commentReport) {
        CommentReport tmpCommentReport = null;

        commentReport.setId(0);

        try {
            tmpCommentReport = repo.save(commentReport);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpCommentReport;
    }

    public CommentReport updateOne(CommentReport commentReport) {
        CommentReport tmpCommentReport = null;

        try {
            repo.findById(commentReport.getId()).get();

            tmpCommentReport = repo.save(commentReport);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpCommentReport;
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

    public Boolean deleteAllByCommentId(Integer commentId) {
        Boolean kk = false;

        try {
            Long ii = repo.deleteByCommentId(commentId);

            if(ii > 0) {
                kk = true;
            }
        } catch (Exception e) {
            //TODO: handle exception
        }

        return kk;
    }
}
