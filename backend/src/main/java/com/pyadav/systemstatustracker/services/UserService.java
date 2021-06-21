package com.pyadav.systemstatustracker.services;

import java.util.List;
import java.util.Optional;

import com.pyadav.systemstatustracker.config.MyUserDetails;
import com.pyadav.systemstatustracker.models.UserModel;
import com.pyadav.systemstatustracker.repositories.UserRepository;
import com.pyadav.systemstatustracker.utils.PasswordUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SystemService systemService;

    @Autowired
    private PasswordUtils passwordUtils;

    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> optUser = userRepository.findByUsername(username);
        if (optUser.isEmpty()) {
            throw new UsernameNotFoundException("User " + username  + " not found.");
        }

        UserModel user = optUser.get();
        return MyUserDetails.build(user);
    }

  

    public ResponseEntity<UserModel> findUser(String userId) {
        if (!authService.validateUser(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
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
        user.setPassword(passwordUtils.encodePassword(user.getPassword()));
        String userId = userRepository.insert(user).getId();
        return ResponseEntity.ok(userId);
    }

    public ResponseEntity<String> deleteUser(String userId) {
        if (!authService.validateUser(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

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
        String userId = user.getId();
        if (!authService.validateUser(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<UserModel> optUser = userRepository.findById(user.getId());
        if (optUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userRepository.save(user);
        return ResponseEntity.ok("User updated.");
    } 

}
