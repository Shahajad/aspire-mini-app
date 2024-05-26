package com.aspire.demo.controller;

import com.aspire.demo.config.JwtUtil;
import com.aspire.demo.model.LoanRequest;
import com.aspire.demo.model.User;
import com.aspire.demo.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/loans/{loanId}/repayments")
@Slf4j
public class RepaymentController {

    @Autowired
    private LoanService loanService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<String> addRepayment(@PathVariable Long loanId, @RequestParam double amount,
                                             HttpServletRequest request) {
        User user = null;
        try{
            String authorizationHeader = request.getHeader("Authorization");
            user = jwtUtil.validateUser(authorizationHeader);
        }catch (Exception e){
            log.error("exception ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

        LoanRequest loanRequestRequest = new LoanRequest();
        loanRequestRequest.setId(loanId);
        loanRequestRequest.setAmount(amount);
        try{
            loanService.addRepayment(loanRequestRequest, user);
            return new ResponseEntity<>("Paid", HttpStatus.OK);
        }catch (Exception e){
            log.error("exception ",e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
