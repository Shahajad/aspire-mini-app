package com.aspire.demo.controller;

import com.aspire.demo.config.JwtUtil;
import com.aspire.demo.model.LoanRequest;
import com.aspire.demo.model.User;
import com.aspire.demo.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RepaymentControllerTest {

    @Mock
    private LoanService loanService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private RepaymentController repaymentController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddRepayment() {
        String authorizationHeader = "Bearer token";
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn(authorizationHeader);
        User user = new User();
        when(jwtUtil.validateUser(authorizationHeader)).thenReturn(user);

        // Prepare request
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setId(1L);
        loanRequest.setAmount(1000);

        // Call controller method
        ResponseEntity<String> response = repaymentController.addRepayment(1L, 1000, request);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(loanService, times(1)).addRepayment(loanRequest, user);
    }
}
