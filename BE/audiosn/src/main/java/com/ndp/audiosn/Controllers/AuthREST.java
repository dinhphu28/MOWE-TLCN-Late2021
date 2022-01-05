package com.ndp.audiosn.Controllers;

import com.ndp.audiosn.Entities.User;
import com.ndp.audiosn.Entities.UserInfo;
import com.ndp.audiosn.Entities.UserRole;
import com.ndp.audiosn.Models.Auth.CredentialReturn;
import com.ndp.audiosn.Models.Auth.ForgetPasswordUpdateModel;
import com.ndp.audiosn.Models.Auth.PasswordChangeModel;
import com.ndp.audiosn.Models.Auth.UserModel;
import com.ndp.audiosn.Services.UserInfoService;
import com.ndp.audiosn.Services.UserRoleService;
import com.ndp.audiosn.Services.UserService;
import com.ndp.audiosn.Utils.Auth.PasswordAuthUtil;
import com.ndp.audiosn.Utils.Auth.JWT.jwtSecurity;
import com.ndp.audiosn.Utils.Auth.JWT.myJWT;
import com.ndp.audiosn.Utils.Mail.SendMailUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
public class AuthREST {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SendMailUtil sendMailUtil;

    // Login
    @PutMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> login(@RequestBody UserModel user) {
        ResponseEntity<Object> entity;

        if(user.getUsername() == null || user.getPassword() == null) {
            entity = new ResponseEntity<>("{ \"Notice\": \"Username and password not be empty\" }", HttpStatus.BAD_REQUEST);
        } else {
            User tmpUser = userService.retrieveOne(user.getUsername());

            if(tmpUser == null) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Invalid username or password\" }", HttpStatus.BAD_REQUEST);  // invalid username
            } else {
                if(tmpUser.getActive()) {
                    PasswordAuthUtil passwordAuthUtil = new PasswordAuthUtil();

                    if(passwordAuthUtil.verifyPassword(user.getPassword(), tmpUser.getPassword())) {
                        // create token here
                        myJWT jwt = new jwtSecurity();

                        String token = jwt.GenerateToken(user.getUsername());

                        if(token == "") {
                            entity = new ResponseEntity<>("{ \"Notice\": \"Invalid username or password\" }", HttpStatus.BAD_REQUEST);  // failed to create token
                        } else {
                            UserRole userRole = userRoleService.retrieveOneByUsername(user.getUsername());

                            String roleStr = "";

                            if(userRole.getRoleId() == 1) {
                                roleStr = "admin";
                            } else if(userRole.getRoleId() == 2) {
                                roleStr = "mod";
                            } else if(userRole.getRoleId() == 3) {
                                roleStr = "norm";
                            }

                            CredentialReturn credentialReturn = new CredentialReturn(user.getUsername(), roleStr, token);

                            entity = new ResponseEntity<>(credentialReturn, HttpStatus.OK);
                        }
                    } else {
                        entity = new ResponseEntity<>("{ \"Notice\": \"Invalid username or password\" }", HttpStatus.BAD_REQUEST);  // invalid password
                    }
                } else {
                    entity = new ResponseEntity<>("{ \"Notice\": \"User was blocked\" }", HttpStatus.LOCKED);
                }
            }
        }

        return entity;
    }

    // Update password

    @PatchMapping(
        value = "/{username}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> updatePassword(@PathVariable("username") String username, @RequestBody PasswordChangeModel passwordChangeModel) {
        ResponseEntity<Object> entity;

        if(passwordChangeModel.getOldValue() == null | passwordChangeModel.getNewValue() == null) {
            entity = new ResponseEntity<>("{ \"Notice\": \"Not be empty\" }", HttpStatus.BAD_REQUEST);
        } else {
            User tmpUser = userService.retrieveOne(username);

            if(tmpUser == null) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Invalid username or password\" }", HttpStatus.BAD_REQUEST);  // invalid username
            } else {
                PasswordAuthUtil passwordAuthUtil = new PasswordAuthUtil();

                if(passwordAuthUtil.verifyPassword(passwordChangeModel.getOldValue(), tmpUser.getPassword())) {

                    String encryptedPassword = passwordAuthUtil.storePassword(passwordChangeModel.getNewValue());
                    User tmpToUpdate = new User(username, encryptedPassword, true);
                    User tmpUserChangedPwd = userService.updateOne(tmpToUpdate);

                    if(tmpUserChangedPwd == null) {
                        entity = new ResponseEntity<>("{ \"Notice\": \"Invalid username or password\" }", HttpStatus.BAD_REQUEST);  // failed to create
                    } else {
                        entity = new ResponseEntity<>("{ \"username\": \"" + username + "\", \"password\": \"" + passwordChangeModel.getNewValue() + "\" }", HttpStatus.OK);
                    }
                } else {
                    entity = new ResponseEntity<>("{ \"Notice\": \"Invalid username or password\" }", HttpStatus.BAD_REQUEST);  // invalid password
                }
            }
        }
        
        return entity;
    }

    // Forget password
    @PatchMapping(
        value = "/forget-password/{username}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> forgetPassword(@PathVariable("username") String username, @RequestBody ForgetPasswordUpdateModel forgetPasswordUpdateModel) {
        ResponseEntity<Object> entity;

        myJWT jwt = new jwtSecurity();

        User tmpUserCk = userService.retrieveOne(username);

        if(tmpUserCk == null) {
            entity = new ResponseEntity<>("{ \"Notice\": \"User not found\" }", HttpStatus.NOT_FOUND);
        } else {

            if(!jwt.VerifyToken(forgetPasswordUpdateModel.getToken(), username)) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Token is not verified!\" }", HttpStatus.CONFLICT);
            } else {

                PasswordAuthUtil passwordAuthUtil = new PasswordAuthUtil();

                String newPasswordEncrypted = passwordAuthUtil.storePassword(forgetPasswordUpdateModel.getPassword());

                User tmpToUpdate = tmpUserCk;

                tmpToUpdate.setPassword(newPasswordEncrypted);

                User tmpSaved = userService.updateOne(tmpToUpdate);

                if(tmpSaved == null) {
                    entity = new ResponseEntity<>("{ \"Notice\": \"Can't change password!\" }", HttpStatus.INTERNAL_SERVER_ERROR);
                } else {
                    entity = new ResponseEntity<>("{ \"Notice\": \"Password changed!\" }", HttpStatus.OK);
                }
            }
        }

        return entity;
    }

    @GetMapping(
        value = "/forget-password/{username}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getTokenForgetPassword(@PathVariable("username") String username) {
        ResponseEntity<Object> entity;

        myJWT jwt = new jwtSecurity();

        UserInfo userInfo = userInfoService.retrieveOne(username);

        if(userInfo == null) {
            entity = new ResponseEntity<>("{ \"Notice\": \"Email of user does not exist or can't receive inbox!\" }", HttpStatus.BAD_REQUEST);
        } else {
            String token = jwt.GenerateForgetPasswordToken(username);

            if(token.equals("")) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Failed to generate token\" }", HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                try {
                    String htmlMsgContent = "<h3>Reset your password</h3>" +
                        "<p>Your token here:</p>" +
                        "<h5>" + token + "</h5>" +
                        "<p>This token will expired after 5 minutes</p>";
    
                    sendMailUtil.sendHtmlEmail(htmlMsgContent, userInfo.getEmail());
    
                    entity = new ResponseEntity<>("{ \"Notice\": \"Check your email!\" }", HttpStatus.OK);
                } catch (Exception e) {
                    //TODO: handle exception
                    entity = new ResponseEntity<>("{ \"Notice\": \"Email of user does not exist or can't receive inbox!\" }", HttpStatus.BAD_REQUEST);
                }
            }
        }

        return entity;
    }

    // Register
    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> register(@RequestBody UserModel user) {
        ResponseEntity<Object> entity;

        if(user.getUsername() == null || user.getPassword() == null) {
            entity = new ResponseEntity<>("{ \"Notice\": \"Username and password not be empty\" }", HttpStatus.BAD_REQUEST);
        } else {
            PasswordAuthUtil passwordAuthUtil = new PasswordAuthUtil();

            String encryptedPassword = passwordAuthUtil.storePassword(user.getPassword());
            User tmpToCreate = new User(user.getUsername(), encryptedPassword, true);
            User tmpSaved = userService.createOne(tmpToCreate);

            if(tmpSaved == null) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Username is existed\" }", HttpStatus.BAD_REQUEST);
            } else {
                UserRole tmpUserRoleToSave = new UserRole(0, user.getUsername(), 3);
                UserRole tmpUserRoleSaved = userRoleService.createOne(tmpUserRoleToSave);

                if(tmpUserRoleSaved == null) {
                    entity = new ResponseEntity<>("{ \"Notice\": \"Failed to create user\" }", HttpStatus.BAD_REQUEST);
                } else {
                    entity = new ResponseEntity<>("{ \"username\": \"" + user.getUsername() + "\", \"password\": \"" + user.getPassword() + "\" }", HttpStatus.OK);
                }
            }
        }
        
        return entity;
    }
}
