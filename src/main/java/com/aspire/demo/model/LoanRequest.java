package com.aspire.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.chain.impl.ContextBase;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest extends ContextBase {
    private Long id;
    private double amount;
    private int term;
    private Status status;
    private List<Repayment> repayments;
    private User user;

    public LoanRequest(double amount, int term, User user){
        this.amount = amount;
        this.term = term;
        this.user = user;
    }
    public LoanRequest(Long id,  User user){
        this.id = id;
        this.user = user;
    }

}
