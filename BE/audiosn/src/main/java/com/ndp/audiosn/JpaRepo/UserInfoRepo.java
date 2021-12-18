package com.ndp.audiosn.JpaRepo;

import com.ndp.audiosn.Entities.UserInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepo extends JpaRepository<UserInfo, String> {
    
}
