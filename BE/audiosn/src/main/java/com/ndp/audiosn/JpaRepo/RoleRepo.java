package com.ndp.audiosn.JpaRepo;

import com.ndp.audiosn.Entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    
}
