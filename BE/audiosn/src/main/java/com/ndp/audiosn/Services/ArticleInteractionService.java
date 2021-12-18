package com.ndp.audiosn.Services;

import java.util.List;

import com.ndp.audiosn.Entities.ArticleInteraction;
import com.ndp.audiosn.JpaRepo.ArticleInteractionRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleInteractionService {
    @Autowired
    private ArticleInteractionRepo repo;

    public List<ArticleInteraction> retrieveAll() {
        return repo.findAll();
    }

    public ArticleInteraction retrieveOne(Integer articleId) {
        ArticleInteraction sth = null;

        try {
            sth = repo.findById(articleId).get();
        } catch (Exception e) {
            //TODO: handle exception
        }

        return sth;
    }

    public ArticleInteraction createOne(ArticleInteraction articleInteraction) {
        ArticleInteraction tmpAI = null;

        Boolean b_existed = true;

        try {
            repo.findById(articleInteraction.getId()).get();
        } catch (Exception e) {
            //TODO: handle exception
            tmpAI = repo.save(articleInteraction);

            b_existed = false;
        }

        if(b_existed) {
            return null;
        } else {
            return tmpAI;
        }
    }

    public ArticleInteraction updateOne(ArticleInteraction articleInteraction) {
        ArticleInteraction tmpAI = null;

        try {
            repo.findById(articleInteraction.getId());

            tmpAI = repo.save(articleInteraction);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpAI;
    }
}
