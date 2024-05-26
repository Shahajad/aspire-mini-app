package com.aspire.demo.util;

import com.aspire.demo.model.Loan;
import com.aspire.demo.model.LoanRequest;

import java.util.List;
import java.util.stream.Collectors;

public class Utility {

    public static Loan translate(LoanRequest loanRequest){
        return Loan.builder()
                .id(loanRequest.getId())
                .term(loanRequest.getTerm())
                .status(loanRequest.getStatus())
                .amount(loanRequest.getAmount())
                .repayments(loanRequest.getRepayments())
                .build();
    }

    public static List<Loan> translate(List<LoanRequest> loanRequests){
        return  loanRequests.stream()
                .map(Utility::translate)
                .collect(Collectors.toList());
    }
}
