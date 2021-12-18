package com.ndp.audiosn.JpaRepo;

import java.util.List;

import com.ndp.audiosn.Entities.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {
    UserRole findByUsername(String username);

    List<UserRole> findByRoleId(Integer roleId);
}
