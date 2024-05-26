package com.aspire.demo.commands;

import com.aspire.demo.annotations.CommandsComponent;
import com.aspire.demo.model.LoanRequest;
import com.aspire.demo.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

@CommandsComponent
@Slf4j
public class CreateRepayments  implements Command {

    private final LoanService loanService;

    public CreateRepayments(LoanService loanService){
        this.loanService = loanService;
    }
    @Override
    public boolean execute(Context context) throws Exception {
        LoanRequest loanRequest =  (LoanRequest) context;
        context = loanService.createRepayment(loanRequest);
        log.info("repayments created : "+context);
        return CONTINUE_PROCESSING;
    }
}
