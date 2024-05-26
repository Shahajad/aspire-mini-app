package com.aspire.demo.manager;

import com.aspire.demo.commands.ApproveLoanApplication;
import com.aspire.demo.commands.NotifyUser;
import com.aspire.demo.service.LoanService;
import org.apache.commons.chain.impl.ChainBase;
import org.springframework.stereotype.Component;

@Component
public class LoanApprovalFlow extends ChainBase {

    public LoanApprovalFlow(LoanService loanService){
        super();
        addCommand(new ApproveLoanApplication(loanService));
        addCommand(new NotifyUser());

    }
}
