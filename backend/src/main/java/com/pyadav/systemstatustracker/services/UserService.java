package com.pyadav.systemstatustracker.services;

import java.util.Optional;

import com.pyadav.systemstatustracker.config.MyUserDetails;
import com.pyadav.systemstatustracker.models.AuthRequest;
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
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<UserModel> optUser = userRepository.findById(id);
        if (optUser.isEmpty()) {
            throw new UsernameNotFoundException("User " + id  + " not found.");
        }

        UserModel user = optUser.get();
        return MyUserDetails.build(user);
    }

    public UserDetails loadUserById(String id) throws UsernameNotFoundException {
        Optional<UserModel> optUser = userRepository.findById(id);
        if (optUser.isEmpty()) {
            throw new UsernameNotFoundException("User " + id + " not found.");
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

    public ResponseEntity<?> updateUser(UserModel user) {
        String userId = user.getId();
        if (!authService.validateUser(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<UserModel> optUser = userRepository.findById(user.getId());
        if (optUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserModel oldUser = optUser.get();
        // Check each field to update
        if (user.getEmail() != null && user.getEmail().equals("") == false) {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("This email is already in use.");
            }
            user.setEmail(oldUser.getEmail());
        }
        else {
            user.setEmail("");
        }

        if (user.getUsername() != null && user.getUsername().equals("") == false) {
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body("Username is taken.");
            }
            user.setUsername(oldUser.getUsername());
        }
        else {
            user.setUsername("");
        }
        String password = user.getPassword();
        if (user.getPassword() != null && user.getPassword().equals("") == false) {
            if (passwordUtils.passwordMatches(user.getPassword(), oldUser.getPassword()) == false) {
                user.setPassword(passwordUtils.encodePassword(password));
                userRepository.save(user);
                return authService.authenticateUser(new AuthRequest(user.getEmail(), user.getUsername(), password, user.getId()));
            }
        }
        else {
            user.setPassword("");
        }

        userRepository.save(user);
        return ResponseEntity.ok("User updated.");
    } 

}
