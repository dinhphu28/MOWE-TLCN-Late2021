package com.ndp.audiosn.Utils.Auth.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class jwtSecurity implements myJWT{

    private String secret = "motconmeo";

    @Override
    public String GenerateToken(String username) {
        String token = "";
        
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // token = "meo";
            token = JWT.create()
                .withIssuer("auth0")
                .withSubject(username)
                .sign(algorithm);
        } catch (Exception e) {
            //Invalid Signing configuration / Couldn't convert Claims.
        }

        return token;
    }

    @Override
    public Boolean VerifyToken(String token, String username) {
        Boolean kk = false;

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .withSubject(username)
                .build();

            DecodedJWT decodedJWT = verifier.verify(token);

            kk = true;
        } catch (Exception e) {
            //TODO: handle exception
        }

        return kk;
    }
}
