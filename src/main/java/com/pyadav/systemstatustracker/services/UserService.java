package com.pyadav.systemstatustracker.services;

import java.util.List;
import java.util.Optional;

import com.pyadav.systemstatustracker.models.UserModel;
import com.pyadav.systemstatustracker.repositories.UserRepository;
import com.pyadav.systemstatustracker.utils.PasswordUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SystemService systemService;

    private PasswordUtils passwordUtils;

    public ResponseEntity<List<UserModel>> findAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    public ResponseEntity<UserModel> findUser(String userId) {
        Optional<UserModel> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserModel user = optUser.get();
        return ResponseEntity.ok(user);
    }
    
    public ResponseEntity<String> addNewUser(UserModel user) {
        Optional<UserModel> optUser = userRepository.findByUsername(user.getUsername());
        if (optUser.isPresent()) {
            return ResponseEntity.badRequest().body("Bad Request: A user with this username already exists!");
        }
        String userId = userRepository.insert(user).getId();
        return ResponseEntity.ok(userId);
    }

    public ResponseEntity<String> deleteUser(String userId) {
        Optional<UserModel> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserModel user = optUser.get();
        if (user.getSystemIds() != null) {
            for (String systemId : user.getSystemIds()) {
                systemService.deleteSystem(userId, systemId);
            }
        }
        userRepository.deleteById(userId);
        return ResponseEntity.ok("User deleted from database.");
    }

    public ResponseEntity<String> updateUser(UserModel user) {
        Optional<UserModel> optUser = userRepository.findById(user.getId());
        if (optUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userRepository.save(user);
        return ResponseEntity.ok("User updated.");
    } 

}
