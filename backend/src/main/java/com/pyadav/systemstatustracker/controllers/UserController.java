package com.pyadav.systemstatustracker.controllers;

import java.util.List;

import com.pyadav.systemstatustracker.models.UserModel;
import com.pyadav.systemstatustracker.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UserController {
    
    @Autowired
    private UserService userService;

  

    @GetMapping(value = "{userId}")
    public ResponseEntity<UserModel> getUser(@PathVariable String userId) { 
        return userService.findUser(userId);
    }
    
    @PostMapping(value = "/new-user") 
    public ResponseEntity<String> addNewUser(@RequestBody UserModel user) {

        return userService.addNewUser(user);
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<String> removeUser(@PathVariable String userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping(value = "/{userId}") 
    public ResponseEntity<String> updateUser(@RequestBody UserModel user) {
        return userService.updateUser(user);
    }
    
}
