package com.ndp.audiosn.Services;

import java.util.List;

import com.ndp.audiosn.Entities.UserRole;
import com.ndp.audiosn.JpaRepo.UserRoleRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRoleService {
    @Autowired
    private UserRoleRepo repo;

    public List<UserRole> retrieveAll() {
        return repo.findAll();
    }

    public UserRole retrieveOne(Integer id) {
        UserRole sth = null;

        try {
            sth = repo.findById(id).get();
        } catch (Exception e) {
            //TODO: handle exception
        }

        return sth;
    }

    public UserRole retrieveOneByUsername(String username) {
        UserRole sth = null;

        try {
            sth = repo.findByUsername(username);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return sth;
    }

    public List<UserRole> retrieveByRole(Integer roleId) {
        return repo.findByRoleId(roleId);
    }

    public UserRole createOne(UserRole userRole) {
        UserRole tmpUserRole = null;

        userRole.setId(0);

        try {
            tmpUserRole = repo.save(userRole);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpUserRole;
    }

    public UserRole updateOne(UserRole userRole) {
        UserRole tmpUserRole = null;

        try {
            repo.findById(userRole.getId()).get();

            tmpUserRole = repo.save(userRole);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpUserRole;
    }

    public Boolean deleteOne(Integer id) {
        Boolean kk = false;

        try {
            repo.deleteById(id);

            kk = true;
        } catch (Exception e) {
            //TODO: handle exception
        }

        return kk;
    }
}
