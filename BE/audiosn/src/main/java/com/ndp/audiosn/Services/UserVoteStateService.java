package com.ndp.audiosn.Services;

import java.util.List;

import com.ndp.audiosn.Entities.UserVoteState;
import com.ndp.audiosn.JpaRepo.UserVoteStateRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserVoteStateService {
    @Autowired
    private UserVoteStateRepo repo;

    public List<UserVoteState> retrieveAll() {
        return repo.findAll();
    }

    public UserVoteState retrieveOne(Integer id) {
        UserVoteState sth = null;

        try {
            sth = repo.findById(id).get();
        } catch (Exception e) {
            //TODO: handle exception
        }

        return sth;
    }

    public List<UserVoteState> retrieveByArticleId(Integer articleId) {
        return repo.findByArticleId(articleId);
    }

    public UserVoteState retrieveOneByArticleIdAndUsername(Integer articleId, String username) {
        UserVoteState tmp = null;

        try {
            tmp = repo.findByArticleIdAndUsername(articleId, username);

        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmp;
    }

    public UserVoteState createOne(UserVoteState userVoteState) {
        UserVoteState tmpUserVoteState = null;

        userVoteState.setId(0);

        try {
            tmpUserVoteState = repo.save(userVoteState);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpUserVoteState;
    }

    public UserVoteState updateOne(UserVoteState userVoteState) {
        UserVoteState tmpUserVoteState = null;

        try {
            repo.findById(userVoteState.getId()).get();

            tmpUserVoteState = repo.save(userVoteState);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpUserVoteState;
    }

    public UserVoteState saveOne(UserVoteState userVoteState) {
        UserVoteState tmpUserVoteState = null;

        userVoteState.setId(0);

        try {
            tmpUserVoteState = repo.save(userVoteState);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpUserVoteState;
    }

    public Boolean deleteAllByArticleId(Integer articleId) {
        Boolean kk = false;

        try {
            Long ii = repo.deleteByArticleId(articleId);

            if(ii > 0) {
                kk = true;
            }
        } catch (Exception e) {
            //TODO: handle exception
        }

        return kk;
    }
}
