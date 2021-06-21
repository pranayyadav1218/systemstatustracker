package com.pyadav.systemstatustracker.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Document(collection = "Users")
@Data
public class UserModel {

    @Id
    private String id;

    @Field
    private String username;

    @Field
    private String email;

    @Field
    private String password;

    @Field
    private List<String> systemIds;

    public UserModel() {}

    public UserModel(String email, String username, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    //Getters

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getSystemIds() {
        return systemIds;
    }

    // Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSystemIds(List<String> systemIds) {
        this.systemIds = systemIds;
    }

    // Other methods

    public void addSystem(String systemId) {
        if (systemIds == null) {
            systemIds = new ArrayList<String>();
        }
    
        systemIds.add(systemId);
    
    }
 
    public void removeSystem(String systemId) {
        if (systemIds != null) {
            systemIds.remove(systemId);
        }
    }


}
