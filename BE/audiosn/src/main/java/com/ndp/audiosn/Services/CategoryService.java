package com.ndp.audiosn.Services;

import java.util.List;

import com.ndp.audiosn.Entities.Category;
import com.ndp.audiosn.JpaRepo.CategoryRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService {
    @Autowired
    private CategoryRepo repo;

    public List<Category> retrieveAll() {
        return repo.findAll();
    }

    public Category retrieveOne(String name) {
        Category sth = null;
        
        try {
            sth = repo.findById(name).get();
        } catch (Exception e) {
            //TODO: handle exception
        }

        return sth;
    }

    public Category createOne(Category category) {
        Category tmpCat = null;

        Boolean b_existed = true;

        try {
            tmpCat = repo.findById(category.getName()).get();
        } catch (Exception e) {
            //TODO: handle exception
            tmpCat = repo.save(category);

            b_existed = false;
        }

        if(b_existed) {
            return null;
        } else {
            return tmpCat;
        }
    }

    public Category updateOne(Category category) {
        Category tmpCat = null;

        try {
            repo.findById(category.getName()).get();

            tmpCat = repo.save(category);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpCat;
    }
}
