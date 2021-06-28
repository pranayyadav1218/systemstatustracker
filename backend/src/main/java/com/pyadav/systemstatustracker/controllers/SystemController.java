package com.pyadav.systemstatustracker.controllers;

import java.util.List;

import com.pyadav.systemstatustracker.models.SystemModel;
import com.pyadav.systemstatustracker.services.SystemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@AllArgsConstructor
@RestController
@RequestMapping("/api/users/{userId}/systems")
@CrossOrigin(origins = "*")
public class SystemController {
    
    @Autowired
    private SystemService systemService;

    @GetMapping("")
    public ResponseEntity<List<SystemModel>> getAllSystems(@PathVariable("userId") String userId) {
        return systemService.findAllSystems(userId);
    }

    @GetMapping("/{systemId}")
    public ResponseEntity<SystemModel> getSystem(@PathVariable("userId") String userId, @PathVariable("systemId") String systemId) {
        return systemService.findSystem(userId, systemId);
    }

    @PostMapping("/new-system")
    public ResponseEntity<String> addSystem(@PathVariable("userId") String userId, @RequestBody SystemModel system) {
        return systemService.addSystem(userId, system);
    }
    
    @PutMapping("/{systemId}")
    public ResponseEntity<String> updateSystem(@PathVariable("userId") String userId, @RequestBody SystemModel system) {
        return systemService.updateSystem(userId, system);
    }

    @DeleteMapping("/{systemId}")
    public ResponseEntity<String> delteSystem(@PathVariable("userId") String userId, @PathVariable("systemId") String systemId) {
        return systemService.deleteSystem(userId, systemId);
    }

}
