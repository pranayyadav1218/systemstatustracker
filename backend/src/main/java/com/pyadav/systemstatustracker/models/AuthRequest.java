package com.pyadav.systemstatustracker.models;

import lombok.Data;

@Data
public class AuthRequest {
   
    
    private String email;

    private String username;
    
    private String password;

    private String id;

    public AuthRequest() {}

    public AuthRequest(String email, String username, String password, String id) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.id = id;
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

    public String getId() {
        return id;
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

    public void setId(String id) {
        this.id = id;
    }
}


