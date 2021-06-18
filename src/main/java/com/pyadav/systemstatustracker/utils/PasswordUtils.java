package com.pyadav.systemstatustracker.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
    private BCryptPasswordEncoder encoder;
    
    public PasswordUtils() {
        this.encoder = new BCryptPasswordEncoder();
    }

    public boolean passwordMatches(String password, String hashedPassword) {
        return encoder.matches(password, hashedPassword);
    }

    public String encodePassword(String password) {
        return encoder.encode(password);
    }
}
