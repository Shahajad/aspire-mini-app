package com.aspire.demo.controller;

import com.aspire.demo.commands.CreateLoanApplication;
import com.aspire.demo.commands.CreateRepayments;
import com.aspire.demo.config.JwtUtil;
import com.aspire.demo.manager.LoanCatalog;
import com.aspire.demo.manager.LoanManager;
import com.aspire.demo.model.Loan;
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

public class LoanControllerTest {

    @Mock
    private LoanService loanService;

    @Mock
    private LoanCatalog loanCatalog;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private LoanManager loanManager;



    @InjectMocks
    private LoanController loanController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateLoan() {
        // Mock JWTUtil
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer adminToken");
        User user = new User();
        when(jwtUtil.validateUser("Bearer adminToken")).thenReturn(user);
        loanManager.addCommand(new CreateLoanApplication(loanService));
        loanManager.addCommand(new CreateRepayments(loanService));

        when(loanCatalog.getCommand("loanManager")).thenReturn(loanManager);

        // Mock LoanRequest and Loan
        LoanRequest loanRequest = new LoanRequest(1000, 12, user);
        Loan loan = new Loan(1000, 12);
        // Call controller method
        ResponseEntity<Loan> createdLoan = loanController.createLoan(1000, 12, request);
        assertEquals(loan, createdLoan.getBody());
    }

    @Test
    public void testApproveLoan() {
        // Mock JWTUtil
        String authorizationHeader = "Bearer token";
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn(authorizationHeader);
        User user = new User();
        when(jwtUtil.validateUser(authorizationHeader)).thenReturn(user);

        // Call controller method
        ResponseEntity<String> response = loanController.approveLoan(1L, request);

        // Verify response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(loanService, times(0)).approveLoan(1L, user);
    }

    @Test
    public void testGetLoan() {
        // Mock JWTUtil
        String authorizationHeader = "Bearer token";
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn(authorizationHeader);
        User user = new User();
        when(jwtUtil.validateUser(authorizationHeader)).thenReturn(user);

        // Mock LoanRequest
        LoanRequest loanRequest = new LoanRequest(1000, 12, user);
        when(loanService.getLoan(user, 1L)).thenReturn(loanRequest);

        // Call controller method
        ResponseEntity<Loan> response = loanController.getLoan(1L, request);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
