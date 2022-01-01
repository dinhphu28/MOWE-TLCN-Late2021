package com.ndp.audiosn.Services;

import java.util.List;

import com.ndp.audiosn.Entities.Article;
import com.ndp.audiosn.JpaRepo.ArticleRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleService {
    @Autowired
    private ArticleRepo repo;

    public List<Article> retrieveAll() {
        return repo.findAll();
    }

    /**
     * 
     * @param pageNumber
     * @return List 10 articles (1 page)
     */
    public List<Article> retrieveOneCommonPage(Integer pageNumber) {
        Page<Article> page = repo.findAll(PageRequest.of(pageNumber, 10, Sort.by("id").descending()));

        List<Article> articles = page.getContent();

        return articles;
    }

    public List<Article> retrieveOneCommonPageAndHidden(Integer pageNumber, Boolean hidden) {
        List<Article> articles = repo.findByHidden(hidden, PageRequest.of(pageNumber, 10, Sort.by("id").descending()));

        return articles;
    }

    public List<Article> retrieveOnePageByCategory(Integer pageNumber, String category) {
        List<Article> articles = repo.findByCategory(category, PageRequest.of(pageNumber, 10, Sort.by("id").descending()));

        return articles;
    }

    public List<Article> retrieveOnePageByCategoryAndHidden(Integer pageNumber, String category, Boolean hidden) {
        List<Article> articles = repo.findByCategoryAndHidden(category, hidden, PageRequest.of(pageNumber, 10, Sort.by("id").descending()));

        return articles;
    }

    public Long retrieveNumOfPages(String categoryName) {
        if(categoryName == null) {
            return repo.count();
        } else {
            return repo.countByCategory(categoryName);
        }
    }

    public Long retrieveNumOfPagesAndHidden(String categoryName, Boolean hidden) {
        if(categoryName == null) {
            return repo.countByHidden(hidden);
        } else {
            return repo.countByCategoryAndHidden(categoryName, hidden);
        }
    }

    public Article retrieveOne(Integer id) {
        Article sth = null;

        try {
            sth = repo.findById(id).get();
        } catch (Exception e) {
            //TODO: handle exception
        }

        return sth;
    }

    public Article createOne(Article article) {
        Article tmpArticle = null;

        article.setId(0);

        try {
            tmpArticle = repo.save(article);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpArticle;
    }

    public Article updateOne(Article article) {
        Article tmpArticle = null;

        try {
            repo.findById(article.getId()).get();

            tmpArticle = repo.save(article);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpArticle;
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
}
