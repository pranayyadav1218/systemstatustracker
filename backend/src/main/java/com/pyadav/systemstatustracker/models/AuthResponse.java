package com.pyadav.systemstatustracker.models;

import lombok.Data;

@Data
public class AuthResponse {
    private String jwtToken;
    private String id;

    public AuthResponse() {}

    public AuthResponse(String jwtToken, String id) {
        this.id = id;
        this.jwtToken = jwtToken;
    }

    // Getters

    public String getJwtToken() {
        return jwtToken;
    }

    public String getId() {
        return id;
    }

    // Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
