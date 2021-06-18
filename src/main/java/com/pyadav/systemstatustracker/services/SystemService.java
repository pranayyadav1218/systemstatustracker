package com.pyadav.systemstatustracker.services;

import java.util.List;
import java.util.Optional;

import com.pyadav.systemstatustracker.models.SystemModel;
import com.pyadav.systemstatustracker.models.UserModel;
import com.pyadav.systemstatustracker.repositories.SystemRepository;
import com.pyadav.systemstatustracker.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SystemService {
    
    @Autowired
    private SystemRepository systemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComponentService componentService;
    
    public ResponseEntity<List<SystemModel>> findAllSystems(String userId) {
        Optional<UserModel> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(systemRepository.findAllByUserId(userId).get());
    }

    public ResponseEntity<SystemModel> findSystem(String userId, String systemId) {
        Optional<SystemModel> optSystem = systemRepository.findByIdAndUserId(systemId, userId);
        if (optSystem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optSystem.get());
    }

    public ResponseEntity<String> addSystem(String userId, SystemModel system) {
        if (userId.equals(system.getUserId())) {
            Optional<UserModel> optUser = userRepository.findById(userId);
            if (optUser.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            String systemId = systemRepository.insert(system).getId();
            UserModel user = optUser.get();
            user.addSystem(systemId);
            userRepository.save(user);
            return ResponseEntity.ok(systemId);
        }
        return ResponseEntity.badRequest().body("Authorization error: User ids do not match!");
    }

    public ResponseEntity<String> updateSystem(String userId, SystemModel system) {
        if (userId.equals(system.getUserId())) {
            Optional<SystemModel> optSystem = systemRepository.findById(system.getId());
            if (optSystem.isPresent()) {
                systemRepository.save(system);
                return ResponseEntity.ok("System updated.");
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().body("Authorization error: User ids do not match!");
    }

    public ResponseEntity<String> deleteSystem(String userId, String systemId) {
        Optional<SystemModel> optSystem = systemRepository.findById(systemId);
        if (optSystem.isPresent()) {
            SystemModel system = optSystem.get();
            if (system.getComponentIds() != null) {
                for (String componentId: system.getComponentIds()) {
                    componentService.deleteComponent(userId, systemId, componentId);
                }
            }
            systemRepository.delete(system);
            UserModel user = userRepository.findById(userId).get();
            user.removeSystem(systemId);
            userRepository.save(user);
            return ResponseEntity.ok().body("System deleted.");
        }
               
        return ResponseEntity.notFound().build();
    }
}
