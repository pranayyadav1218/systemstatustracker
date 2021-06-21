package com.pyadav.systemstatustracker.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Document(collection = "Systems")
@Data
public class SystemModel {
    
    @Id
    private String id;

    @Field
    private String name;

    @Field
    private String userId;

    @Field
    private boolean status;

    @Field
    private List<String> componentIds;

    //Getters

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public boolean getStatus() {
        return status;
    }

    public List<String> getComponentIds() {
        return componentIds;
    }

    // Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setComponetIds(List<String> componentIds) {
        this.componentIds = componentIds;
    }

    // Other methods

    
    public void addComponent(String componentId) {
        if (componentIds == null) {
            componentIds = new ArrayList<String>();
        }
    
        componentIds.add(componentId);
    
    }
 
    public void removeComponent(String componentId) {
        if (componentIds != null) {
            componentIds.remove(componentId);
        }
    }

}
