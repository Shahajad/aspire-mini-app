package com.aspire.demo.manager;

import com.aspire.demo.model.LoanRequest;
import org.apache.commons.chain.impl.CatalogBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoanCatalog extends CatalogBase {

    public LoanCatalog(LoanManager loanManager, LoanApprovalFlow loanApprovalFlow){
        super();
        addCommand("loanManager", loanManager);
        addCommand("loanApprovalFlow", loanApprovalFlow);
    }

}
