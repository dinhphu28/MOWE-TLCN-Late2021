package com.ndp.audiosn.Controllers;

import com.ndp.audiosn.Entities.UserInfo;
import com.ndp.audiosn.Models.UserInfo.UserInfoReturnModel;
import com.ndp.audiosn.Models.UserInfo.UserInfoUpdateModel;
import com.ndp.audiosn.Services.UserInfoService;
import com.ndp.audiosn.Utils.Auth.JWT.jwtSecurity;
import com.ndp.audiosn.Utils.Auth.JWT.myJWT;
import com.ndp.audiosn.Utils.Auth.TokenProcessing.AuthHeaderProcessing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/profiles")
public class UserInfoREST {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AuthHeaderProcessing authHeaderProcessing;

    @GetMapping(
        value = "/{username}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> retrieveOneProfile(@PathVariable("username") String username) {
        ResponseEntity<Object> entity;

        UserInfo userInfo = userInfoService.retrieveOne(username);

        if(userInfo != null) {
            UserInfoReturnModel userInfoReturnModel = new UserInfoReturnModel(userInfo);

            entity = new ResponseEntity<>(userInfoReturnModel, HttpStatus.OK);
        } else {
            entity = new ResponseEntity<>("{ \"Notice\": \"Not found\" }", HttpStatus.NOT_FOUND);
        }

        return entity;
    }

    @PutMapping(
        value = "/{username}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> updateProfileInfo(@PathVariable("username") String username, @RequestBody UserInfoUpdateModel userInfoUpdateModel, @RequestHeader("Authorization") String authorization) {
        ResponseEntity<Object> entity;

        String token = authHeaderProcessing.getTokenFromAuthHeader(authorization);

        myJWT jwt = new jwtSecurity();

        Boolean authorized = jwt.VerifyToken(token, username);

        if(authorized) {
            if(userInfoUpdateModel.getAvatar() == null) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Not null\" }", HttpStatus.BAD_REQUEST);
            } else {
    
                UserInfo tmpToSave = userInfoUpdateModel.toUserInfoEntity(username);
    
                UserInfo tmpUserInfo = userInfoService.saveOne(tmpToSave);
    
                if(tmpUserInfo == null) {
                    entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.BAD_REQUEST);
                } else {
                    entity = new ResponseEntity<>(tmpUserInfo, HttpStatus.OK);
                }
            }
        } else {
            entity = new ResponseEntity<>("{ \"Notice\": \"Unauthorized\" }", HttpStatus.UNAUTHORIZED);
        }

        return entity;
    }
}
