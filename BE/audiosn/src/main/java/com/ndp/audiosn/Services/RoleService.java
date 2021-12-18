package com.ndp.audiosn.Services;

import java.util.List;

import com.ndp.audiosn.Entities.Role;
import com.ndp.audiosn.JpaRepo.RoleRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleRepo repo;

    public List<Role> retrieveAll() {
        return repo.findAll();
    }

    public Role retrieveOne(Integer id) {
        Role sth = null;

        try {
            sth = repo.findById(id).get();
        } catch (Exception e) {
            //TODO: handle exception
        }

        return sth;
    }

    public Role createOne(Role role) {
        Role tmpRole = null;

        role.setId(0);

        try {
            tmpRole = repo.save(role);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpRole;
    }

    public Role updateOne(Role role) {
        Role tmpRole = null;

        try {
            repo.findById(role.getId()).get();

            tmpRole = repo.save(role);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpRole;
    }

    public Boolean deleteOne(Integer id) {
        Boolean kk = false;

        try {
            repo.findById(id).get();

            repo.deleteById(id);

            kk = true;
        } catch (Exception e) {
            //TODO: handle exception
        }

        return kk;
    }
}
