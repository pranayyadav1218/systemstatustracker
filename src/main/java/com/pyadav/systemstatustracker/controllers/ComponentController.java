package com.pyadav.systemstatustracker.controllers;

import java.util.List;

import com.pyadav.systemstatustracker.models.ComponentModel;
import com.pyadav.systemstatustracker.services.ComponentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users/{userId}/systems/{systemId}/components")
public class ComponentController {
    
    @Autowired
    private ComponentService componentService;

    @GetMapping("") 
    public ResponseEntity<List<ComponentModel>> getAllComponents(@PathVariable("userId") String userId, @PathVariable("systemId") String systemId) {
        return componentService.findAllComponents(userId, systemId);
    }

    @GetMapping("/{componentId}")
    public ResponseEntity<ComponentModel> getComponent(@PathVariable("userId") String userId, @PathVariable("systemId") String systemId, @PathVariable String componentId) {
        return componentService.findComponent(userId, systemId, componentId);
    }

    @PostMapping("/new-component")
    public ResponseEntity<String> addComponent(@PathVariable("userId") String userId, @PathVariable("systemId") String systemId, @RequestBody ComponentModel component) {
        return componentService.addNewComponent(userId, systemId, component);
    }
    
    @DeleteMapping("/{componentId}")
    public ResponseEntity<String> deleteComponent(@PathVariable("userId") String userId, @PathVariable("systemId") String systemId, @PathVariable("componentId") String componentId) {
        return componentService.deleteComponent(userId, systemId, componentId);
    }
}
