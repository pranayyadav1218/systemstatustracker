package com.pyadav.systemstatustracker.services;

import java.util.List;
import java.util.Optional;

import com.pyadav.systemstatustracker.models.ComponentModel;
import com.pyadav.systemstatustracker.models.SystemModel;
import com.pyadav.systemstatustracker.repositories.ComponentRepository;
import com.pyadav.systemstatustracker.repositories.SystemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ComponentService {
    
    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private SystemRepository systemRepository;

    public ResponseEntity<List<ComponentModel>> findAllComponents(String userId, String systemId) {
        return ResponseEntity.ok(componentRepository.findAllByUserIdAndSystemId(userId, systemId).get());
    }

    public ResponseEntity<ComponentModel> findComponent(String userId, String systemId, String componentId) {
        Optional<ComponentModel> optComponent = componentRepository.findByIdAndUserIdAndSystemId(componentId, userId, systemId);
        if (optComponent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optComponent.get());
    }

    public ResponseEntity<String> addNewComponent(String userId, String systemId, ComponentModel component) {
        if (component.getUserId().equals(userId) && component.getSystemId().equals(systemId)) {
            Optional<SystemModel> optSystem = systemRepository.findByIdAndUserId(systemId, userId);
            if (optSystem.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            String componentId = componentRepository.insert(component).getId();
            SystemModel system = optSystem.get();
            system.addComponent(componentId);
            systemRepository.save(system);
            return ResponseEntity.ok(componentId);
        }
        else if (component.getUserId().equals(userId) == false) {
            return ResponseEntity.badRequest().body("Bad request: user id's do not match!");
        }
        else if (component.getSystemId().equals(systemId) == false) {
            return ResponseEntity.badRequest().body("Bad request: system id's do not match!");
        }
        return ResponseEntity.badRequest().body("Unable to add component.");
    }

    public ResponseEntity<String> updateComponent(String userId, String systemId, ComponentModel component) {
        if (component.getUserId().equals(userId) && component.getSystemId().equals(systemId)) {
            Optional<ComponentModel> optComponent = componentRepository.findByIdAndUserIdAndSystemId(component.getId(), userId, systemId);
            if (optComponent.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            componentRepository.save(component);
            return ResponseEntity.ok("Component updated.");
        }
        else if (component.getUserId().equals(userId) == false) {
            return ResponseEntity.badRequest().body("Bad request: user id's do not match!");
        }
        else if (component.getSystemId().equals(systemId) == false) {
            return ResponseEntity.badRequest().body("Bad request: system id's do not match!");
        }
        return ResponseEntity.badRequest().body("Bad request: Unable to update component.");
    }

    public ResponseEntity<String> deleteComponent(String userId, String systemId, String componentId) {
        
        Optional<ComponentModel> optComponent = componentRepository.findByIdAndUserIdAndSystemId(componentId, userId, systemId);
        if (optComponent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        componentRepository.delete(optComponent.get());
        SystemModel system = systemRepository.findByIdAndUserId(systemId, userId).get();
        system.removeComponent(componentId);
        systemRepository.save(system);
        return ResponseEntity.ok("Component removed.");

    }
}