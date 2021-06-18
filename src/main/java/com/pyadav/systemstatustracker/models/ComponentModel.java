package com.pyadav.systemstatustracker.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Document(collection = "Components")
@Data
public class ComponentModel {
    
    @Id
    private String id;

    @Field
    private String name;

    @Field
    private String userId;

    @Field
    private String systemId;

    @Field
    private boolean status;

    // Getters

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getSystemId() {
        return systemId;
    }

    public boolean getStatus() {
        return status;
    }

    // Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


}


