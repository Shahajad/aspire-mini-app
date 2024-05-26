package com.aspire.demo.controller;

import com.aspire.demo.config.JwtUtil;
import com.aspire.demo.model.Role;
import com.aspire.demo.model.User;
import com.aspire.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(String userName, String password, Role role,
                                               HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        User adminUser = jwtUtil.validateUser(authorizationHeader);
        if(!adminUser.getRoles().contains(Role.ADMIN)){
            return new ResponseEntity<>("Only Admin can create new users", HttpStatus.UNAUTHORIZED);
        }
        try {

            User user = User.builder()
                    .username(userName)
                    .password(password)
                    .roles(new HashSet<>(Collections.singletonList(role)))
                    .build();
            User newUser = userService.registerUser(user);
            return new ResponseEntity<>(newUser.getId(), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Handle user already exists error
            log.error("error while creating user "+ e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
