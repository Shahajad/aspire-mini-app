package com.aspire.demo.commands;

import com.aspire.demo.model.LoanRequest;
import com.aspire.demo.model.Status;
import com.aspire.demo.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
@Slf4j
public class CreateLoanApplication implements Command {

    private final LoanService loanService;

    public CreateLoanApplication(LoanService loanService){
        this.loanService = loanService;
    }
    @Override
    public boolean execute(Context context) throws Exception {

        LoanRequest loanRequest =  (LoanRequest) context;
        loanRequest.setId(generateLoanId());
        loanRequest.setStatus(Status.IN_PROCESS);
        log.info("loan request created");
        return CONTINUE_PROCESSING;
    }

    public Long generateLoanId(){
        return  loanService.getNextLoanId();
    }
}
