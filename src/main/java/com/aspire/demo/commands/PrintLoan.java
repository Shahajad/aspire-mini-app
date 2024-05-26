package com.aspire.demo.commands;

import com.aspire.demo.annotations.CommandsComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

@CommandsComponent
@Slf4j
public class PrintLoan implements Command {
    @Override
    public boolean execute(Context context) throws Exception {
        log.info("printing loanRequest: "+ context);
        return CONTINUE_PROCESSING;
    }
}
