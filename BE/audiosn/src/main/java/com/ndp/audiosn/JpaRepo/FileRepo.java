package com.ndp.audiosn.JpaRepo;

import com.ndp.audiosn.Entities.File;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<File, String> {
    
}
