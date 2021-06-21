package com.pyadav.systemstatustracker.services;

import com.pyadav.systemstatustracker.models.AuthResponse;
import com.pyadav.systemstatustracker.config.MyUserDetails;
import com.pyadav.systemstatustracker.models.AuthRequest;
import com.pyadav.systemstatustracker.models.UserModel;
import com.pyadav.systemstatustracker.repositories.UserRepository;
import com.pyadav.systemstatustracker.utils.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    public ResponseEntity<?> registerUser(AuthRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: an account is already associated with this email!");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: username is already taken!");
        }

        UserModel user = new UserModel();
        user.setEmail(request.getEmail());
        System.out.println(request.getEmail() + request.getUsername() + request.getPassword());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setUsername(request.getUsername());
        
        userRepository.insert(user);

        return authenticateUser(new AuthRequest(request.getEmail(), request.getUsername(), request.getPassword()));
    }

    public ResponseEntity<?> authenticateUser(AuthRequest request) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtUtils.generateJwtToken(authentication);

        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new AuthResponse(jwtToken, userDetails.getId()));

    }

    public boolean validateUser(String userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
        UserModel user = userRepository.findById(userId).get();
        if (userDetails.getUsername().equals(user.getUsername()) && userDetails.getPassword().equals(user.getPassword())) {
            return true;
        }       

        return false;
    }

}
