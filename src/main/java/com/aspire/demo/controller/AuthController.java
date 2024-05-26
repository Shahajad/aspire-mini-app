package com.aspire.demo.controller;

import com.aspire.demo.config.JwtUtil;
import com.aspire.demo.model.AuthRequest;
import com.aspire.demo.model.Role;
import com.aspire.demo.model.User;
import com.aspire.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest request) {
        String userId = request.getUserId();
        String password = request.getUserPassword();

        // Perform password validation logic (e.g., check against database)
        if (isValidPassword(userId, password)) {
            // If password is valid, generate JWT token
            User user = userService.loadUserByUserId(userId);
            String token = JwtUtil.generateToken(user);
            return ResponseEntity.ok(token);
        } else {
            // If password is invalid, return unauthorized status
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    private boolean isValidPassword(String userId, String password) {
        User user = userService.loadUserByUserId(userId);
        if(user == null) return false;
        return user.getPassword().equals(password);
    }
}
