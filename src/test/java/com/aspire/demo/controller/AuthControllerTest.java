package com.aspire.demo.controller;

import com.aspire.demo.model.AuthRequest;
import com.aspire.demo.model.User;
import com.aspire.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticate_ValidCredentials() {
        // Mock UserService
        String userId = "testUser";
        String password = "testPassword";
        User user = new User();
        user.setId(userId);
        user.setPassword(password);
        when(userService.loadUserByUserId(userId)).thenReturn(user);

        // Prepare request
        AuthRequest request = new AuthRequest();
        request.setUserId(userId);
        request.setUserPassword(password);

        // Call controller method
        ResponseEntity<String> response = authController.authenticate(request);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testAuthenticate_InvalidCredentials() {
        // Mock UserService
        String userId = "testUser";
        String password = "testPassword";
        when(userService.loadUserByUserId(userId)).thenReturn(null); // Simulating user not found

        // Prepare request
        AuthRequest request = new AuthRequest();
        request.setUserId(userId);
        request.setUserPassword(password);

        // Call controller method
        ResponseEntity<String> response = authController.authenticate(request);

        // Verify response
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody());
    }
}
