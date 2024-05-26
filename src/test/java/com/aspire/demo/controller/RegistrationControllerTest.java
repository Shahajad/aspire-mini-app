package com.aspire.demo.controller;

import com.aspire.demo.config.JwtUtil;
import com.aspire.demo.model.Role;
import com.aspire.demo.model.User;
import com.aspire.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RegistrationControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private RegistrationController registrationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser_AdminAuthorization_Success() {
        // Mock HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer adminToken");

        // Mock JwtUtil
        User adminUser = new User();
        adminUser.setRoles(Collections.singleton(Role.ADMIN));
        when(jwtUtil.validateUser("Bearer adminToken")).thenReturn(adminUser);

        // Prepare request parameters
        String userName = "testUser";
        String password = "testPassword";
        Role role = Role.USER;

        // Mock UserService
        User newUser = new User();
        newUser.setId("123");
        when(userService.registerUser(any(User.class))).thenReturn(newUser);

        // Call controller method
        ResponseEntity<String> response = registrationController.registerUser(userName, password, role, request);

        // Verify response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("123", response.getBody());
    }

    @Test
    public void testRegisterUser_NonAdminAuthorization_Unauthorized() {
        // Mock HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer nonAdminToken");

        // Mock JwtUtil
        User nonAdminUser = new User();
        nonAdminUser.setRoles(Collections.singleton(Role.USER));
        when(jwtUtil.validateUser("Bearer nonAdminToken")).thenReturn(nonAdminUser);

        // Call controller method
        ResponseEntity<String> response = registrationController.registerUser("testUser", "testPassword", Role.USER, request);

        // Verify response
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Only Admin can create new users", response.getBody());
    }

    // Add more test cases as needed
}
