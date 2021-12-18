package com.ndp.audiosn.JpaRepo;

import com.ndp.audiosn.Entities.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, String> {
    
}
