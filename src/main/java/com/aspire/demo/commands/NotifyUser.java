package com.aspire.demo.commands;

import com.aspire.demo.annotations.CommandsComponent;
import com.aspire.demo.model.LoanRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
@CommandsComponent
@Slf4j
public class NotifyUser implements Command {
    @Override
    public boolean execute(Context context) throws Exception {
        LoanRequest loanRequest = (LoanRequest) context;
        log.info("user {} notified by email for loan id {}",
                loanRequest.getUser().getId(),
                loanRequest.getId());
        return false;
    }
}
