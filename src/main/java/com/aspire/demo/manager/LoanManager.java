package com.aspire.demo.manager;

import com.aspire.demo.commands.ApproveLoanApplication;
import com.aspire.demo.commands.CreateLoanApplication;
import com.aspire.demo.commands.CreateRepayments;
import com.aspire.demo.commands.PrintLoan;
import com.aspire.demo.service.LoanService;
import org.apache.commons.chain.impl.ChainBase;
import org.springframework.stereotype.Component;

@Component
public class LoanManager extends ChainBase {

    public LoanManager(LoanService loanService){
        super();
        addCommand(new CreateLoanApplication(loanService));
        addCommand(new CreateRepayments(loanService));
    }
}
