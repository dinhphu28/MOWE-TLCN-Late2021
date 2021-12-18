package com.ndp.audiosn.Utils.Auth;

import com.ndp.audiosn.Utils.Hashing.BCryptUtil;

public class PasswordAuthUtil {
    public String storePassword(String password) {
        BCryptUtil bcu = new BCryptUtil();

        String hashedpasswd = bcu.passwordEncoder().encode(password);

        return hashedpasswd;
    }

    public Boolean verifyPassword(String password, String encodedPassword) {
        BCryptUtil bcu = new BCryptUtil();

        Boolean kk = false;

        kk = bcu.passwordEncoder().matches(password, encodedPassword);

        return kk;
    }
}
