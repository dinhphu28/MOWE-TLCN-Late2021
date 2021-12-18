package com.ndp.audiosn.Utils.Auth.JWT;

public interface myJWT {
    String GenerateToken(String username);
    
    Boolean VerifyToken(String token, String username);
}
