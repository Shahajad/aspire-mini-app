package com.aspire.demo.controller;

import com.aspire.demo.config.JwtUtil;
import com.aspire.demo.manager.LoanCatalog;
import com.aspire.demo.model.Loan;
import com.aspire.demo.model.LoanRequest;
import com.aspire.demo.model.User;
import com.aspire.demo.service.LoanService;
import com.aspire.demo.util.Utility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/loans")
@Slf4j
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired LoanCatalog loanCatalog;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping
    public Loan createLoan(@RequestParam double amount, @RequestParam int term,
                           HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        User user = jwtUtil.validateUser(authorizationHeader);
        LoanRequest loanRequest = new LoanRequest(amount, term, user);
        Command loanManager = loanCatalog.getCommand("loanManager");

        try {
            loanManager.execute(loanRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("loan created {}", loanRequest.getId());
        return Utility.translate(loanRequest);
    }

    @PutMapping("/{loanId}/approve")
    public ResponseEntity<String> approveLoan(@PathVariable Long loanId,
                                              HttpServletRequest request) {
        try{
            String authorizationHeader = request.getHeader("Authorization");
            User user = jwtUtil.validateUser(authorizationHeader);
            Command loanApprovalFlow = loanCatalog.getCommand("loanApprovalFlow");
            loanApprovalFlow.execute(new LoanRequest(loanId, user));
            return new ResponseEntity<>("Approved", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<Loan> getLoan(@PathVariable Long loanId,
                                        HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        User user = jwtUtil.validateUser(authorizationHeader);
        LoanRequest loanRequest = loanService.getLoan(user, loanId);
        if (loanRequest == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Utility.translate(loanRequest));
    }
}