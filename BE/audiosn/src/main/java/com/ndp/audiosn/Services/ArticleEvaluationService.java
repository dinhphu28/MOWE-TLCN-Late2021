package com.ndp.audiosn.Services;

import java.util.List;

import com.ndp.audiosn.Entities.ArticleEvaluation;
import com.ndp.audiosn.JpaRepo.ArticleEvaluationRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleEvaluationService {
    @Autowired
    private ArticleEvaluationRepo repo;

    public List<ArticleEvaluation> retrieveAll() {
        return repo.findAll();
    }

    public ArticleEvaluation retrieveOne(Integer articleId) {
        ArticleEvaluation sth = null;

        try {
            sth = repo.findById(articleId).get();
        } catch (Exception e) {
            //TODO: handle exception
        }

        return sth;
    }

    public ArticleEvaluation createOne(ArticleEvaluation articleEvaluation) {
        ArticleEvaluation tmpArticleEvaluation = null;

        try {
            repo.findById(articleEvaluation.getArticleId()).get();
        } catch (Exception e) {
            //TODO: handle exception

            try {
                tmpArticleEvaluation = repo.save(articleEvaluation);
            } catch (Exception e2) {
                //TODO: handle exception
            }
        }

        return tmpArticleEvaluation;
    }

    public ArticleEvaluation updateOne(ArticleEvaluation articleEvaluation) {
        ArticleEvaluation tmpArticleEvaluation = null;

        try {
            repo.findById(articleEvaluation.getArticleId()).get();

            tmpArticleEvaluation = repo.save(articleEvaluation);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpArticleEvaluation;
    }
}
