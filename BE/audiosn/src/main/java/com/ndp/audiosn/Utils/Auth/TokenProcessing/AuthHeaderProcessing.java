package com.ndp.audiosn.Utils.Auth.TokenProcessing;

import org.springframework.stereotype.Component;

@Component
public class AuthHeaderProcessing {
    public String getTokenFromAuthHeader(String authorization) {
        String token = authorization.substring(7);

        return token;
    }
}
