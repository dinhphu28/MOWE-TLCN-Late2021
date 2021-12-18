package com.ndp.audiosn.JpaRepo;

import com.ndp.audiosn.Entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
    
}
