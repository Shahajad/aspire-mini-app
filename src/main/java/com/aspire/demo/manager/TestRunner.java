//package com.aspire.demo.manager;
//
//import com.aspire.demo.model.LoanRequest;
//import com.aspire.demo.service.LoanService;
//import org.apache.commons.chain.Catalog;
//import org.apache.commons.chain.Command;
//import org.apache.commons.chain.Context;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
//@Component
//public class TestRunner {
//
////    @Autowired
////    Catalog catalog;
//
//    @Autowired
//    LoanService loanService;
//
//    @Autowired
//    LoanManager loanManager;
//    @Autowired
//    LoanApprovalFlow loanApprovalFlow;
//
////    @PostConstruct
//    public void run(){
//        Context context = new LoanRequest();
//
//        Catalog catalog = new LoanCatalog(loanManager);
//        Command loanManager = catalog.getCommand("loanManager");
////        loanManager = catalog.getCommand("");
//
//        try {
//            loanManager.execute(context);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
