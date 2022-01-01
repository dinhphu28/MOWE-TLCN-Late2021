package com.ndp.audiosn.Services;

import java.util.List;

import com.ndp.audiosn.Entities.ArticleReport;
import com.ndp.audiosn.JpaRepo.ArticleReportRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleReportService {
    @Autowired
    private ArticleReportRepo repo;

    public List<ArticleReport> retrieveAll() {
        // return repo.findAll();
        return repo.findByOrderByIdDesc();
    }

    public List<ArticleReport> retrieveAllByArticleId(Integer articleId) {
        // return repo.findByArticleId(articleId);
        return repo.findByArticleIdOrderByIdDesc(articleId);
    }

    public List<ArticleReport> retrieveBySolved(Boolean solved) {
        return repo.findBySolved(solved);
    }

    public ArticleReport retrieveOne(Integer id) {
        ArticleReport sth = null;

        try {
            sth = repo.findById(id).get();
        } catch (Exception e) {
            //TODO: handle exception
        }

        return sth;
    }

    public ArticleReport createOne(ArticleReport articleReport) {
        ArticleReport tmpArticleReport = null;

        articleReport.setId(0);

        try {
            tmpArticleReport = repo.save(articleReport);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpArticleReport;
    }

    public ArticleReport updateOne(ArticleReport articleReport) {
        ArticleReport tmpArticleReport = null;

        try {
            repo.findById(articleReport.getId()).get();

            tmpArticleReport = repo.save(articleReport);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpArticleReport;
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
