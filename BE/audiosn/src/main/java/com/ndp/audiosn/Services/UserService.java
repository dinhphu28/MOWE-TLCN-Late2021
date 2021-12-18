package com.ndp.audiosn.Services;

import java.util.List;

import com.ndp.audiosn.Entities.User;
import com.ndp.audiosn.JpaRepo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepo repo;

    public User retrieveOne(String username) {
        User sth = null;

        try {
            sth = repo.findById(username).get();
        } catch (Exception e) {
            //TODO: handle exception
        }

        return sth;
    }

    public List<User> retrieveAll() {
        return repo.findAll();
    }

    public User createOne(User user) {
        User tmpUser = null;

        Boolean b_existed = true;

        try {
            tmpUser = repo.findById(user.getUsername()).get();
        } catch (Exception e) {
            //TODO: handle exception
            tmpUser = repo.save(user);

            b_existed = false;
        }

        if(b_existed) {
            return null;
        } else {
            return tmpUser;
        }
    }

    public User updateOne(User user) {
        User tmpUser = null;

        try {
            repo.findById(user.getUsername()).get();

            tmpUser = repo.save(user);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpUser;
    }
}
