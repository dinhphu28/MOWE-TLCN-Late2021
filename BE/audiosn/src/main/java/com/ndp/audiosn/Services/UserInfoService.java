package com.ndp.audiosn.Services;

import java.util.List;

import com.ndp.audiosn.Entities.UserInfo;
import com.ndp.audiosn.JpaRepo.UserInfoRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserInfoService {
    @Autowired
    private UserInfoRepo repo;

    public List<UserInfo> retrieveAll() {
        return repo.findAll();
    }

    public UserInfo retrieveOne(String username) {
        UserInfo sth = null;

        try {
            sth = repo.findById(username).get();
        } catch (Exception e) {
            //TODO: handle exception
        }

        return sth;
    }

    public UserInfo createOne(UserInfo userInfo) {
        UserInfo tmpUserInfo = null;

        try {
            repo.findById(userInfo.getUsername()).get();
        } catch (Exception e) {
            //TODO: handle exception
            tmpUserInfo = repo.save(userInfo);
        }

        return tmpUserInfo;
    }

    public UserInfo updateOne(UserInfo userInfo) {
        UserInfo tmpUserInfo = null;

        try {
            repo.findById(userInfo.getUsername()).get();

            tmpUserInfo = repo.save(userInfo);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpUserInfo;
    }

    public UserInfo saveOne(UserInfo userInfo) {
        UserInfo tmpUserInfo = null;

        try {
            tmpUserInfo = repo.save(userInfo);

        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpUserInfo;
    }
}
