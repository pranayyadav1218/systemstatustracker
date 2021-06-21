package com.pyadav.systemstatustracker.models;

import lombok.Data;

@Data
public class AuthRequest {
   
    
    private String email;

    private String username;
    
    private String password;

    public AuthRequest() {}

    public AuthRequest(String email, String username, String password) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    // Getters

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    // Setters

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


