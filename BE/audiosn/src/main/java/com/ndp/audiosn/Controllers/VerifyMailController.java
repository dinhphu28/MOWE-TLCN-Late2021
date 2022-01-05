package com.ndp.audiosn.Controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ndp.audiosn.Entities.UserInfo;
import com.ndp.audiosn.Models.EmailVerification.EmailVerificationCreateModel;
import com.ndp.audiosn.Services.UserInfoService;
import com.ndp.audiosn.Utils.Auth.JWT.jwtSecurity;
import com.ndp.audiosn.Utils.Auth.JWT.myJWT;
import com.ndp.audiosn.Utils.Mail.SendMailUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/email/verification")
public class VerifyMailController {
    @Autowired
    private SendMailUtil sendMailUtil;

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping(
        value = "/{token}",
        produces = MediaType.TEXT_HTML_VALUE
    )
    public ResponseEntity<Object> verifyEmail(@PathVariable("token") String token) {
        ResponseEntity<Object> entity;

        myJWT jwt = new jwtSecurity();

        DecodedJWT jwtDecoded = JWT.decode(token);

        String username = jwtDecoded.getSubject();
        // String email = jwtDecoded.getClaim("userEmail").asString();

        UserInfo userInfo = userInfoService.retrieveOne(username);

        if(userInfo == null) {
            entity = new ResponseEntity<>("{ \"Notice\": \"Email is not verified!\" }", HttpStatus.UNAUTHORIZED);
        } else {
            Boolean verified = jwt.VerifyEmailVerificationToken(token, userInfo.getUsername(), userInfo.getEmail());

            if(verified) {
                userInfo.setEmailVerified(true);

                UserInfo tmpSaved = userInfoService.saveOne(userInfo);

                if(tmpSaved == null) {
                    entity = new ResponseEntity<>("{ \"Notice\": \"Failed!\" }", HttpStatus.OK);
                } else {
                    String htmlPageRedirect = "<meta http-equiv='refresh' content='0; url=http://localhost:3000/' />" +
                        "<p><a href='http://localhost:3000/'>Redirect</a></p>";

                    // entity = new ResponseEntity<>("{ \"Notice\": \"Email is verified!\" }", HttpStatus.OK);
                    entity = new ResponseEntity<>(htmlPageRedirect, HttpStatus.OK);
                }
            } else {
                entity = new ResponseEntity<>("{ \"Notice\": \"Email is not verified!\" }", HttpStatus.UNAUTHORIZED);
            }
        }

        return entity;
    }

    @PostMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> createEmailVerification(@RequestBody EmailVerificationCreateModel emailVerificationCreateModel) {
        ResponseEntity<Object> entity;

        myJWT jwt = new jwtSecurity();

        UserInfo userInfo = userInfoService.retrieveOne(emailVerificationCreateModel.getUsername());

        if(userInfo == null) {
            entity = new ResponseEntity<>("{ \"Notice\": \"Not have info\" }", HttpStatus.NOT_FOUND);
        } else {
            if(userInfo.getEmail().equals("") || userInfo.getEmail() == null) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Email is empty\" }", HttpStatus.BAD_REQUEST);
            } else {
                String token = jwt.GenerateEmailVerificationToken(emailVerificationCreateModel.getUsername(), userInfo.getEmail());

                if(token.equals("")) {
                    entity = new ResponseEntity<>("{ \"Notice\": \"Failed to generate token\" }", HttpStatus.INTERNAL_SERVER_ERROR);
                } else {

                    try {
                        String htmlMsgContent = "<h3>Email verification</h3>" +
                            "<p>Click the link to verify your email.</p>" +
                            "<a href='" + ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/email/verification/" + token).toUriString() + "'>" + ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/email/verification/" + token).toUriString() + "</a>" +
                            "<p>This link will expired after 5 minutes</p>";

                        sendMailUtil.sendHtmlEmail(htmlMsgContent, userInfo.getEmail());

                        entity = new ResponseEntity<>("{ \"Notice\": \"Check your email!\" }", HttpStatus.CREATED);
                    } catch (Exception e) {
                        //TODO: handle exception
                        entity = new ResponseEntity<>("{ \"Notice\": \"Email is invalid or can't receive inbox\" }", HttpStatus.BAD_REQUEST);
                    }
                }
            }
        }

        return entity;
    }
}
