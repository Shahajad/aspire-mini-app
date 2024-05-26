package com.aspire.demo.commands;

import com.aspire.demo.model.LoanRequest;
import com.aspire.demo.model.Status;
import com.aspire.demo.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
@Slf4j
public class ApproveLoanApplication implements Command {
    private final LoanService loanService;

    public ApproveLoanApplication(LoanService loanService){
        this.loanService = loanService;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        LoanRequest loanRequest =  (LoanRequest) context;
        loanService.approveLoan(loanRequest.getId(), loanRequest.getUser());
        loanRequest.setStatus(Status.APPROVED);
        log.info("loan request Approved");
        return CONTINUE_PROCESSING;
    }
}
