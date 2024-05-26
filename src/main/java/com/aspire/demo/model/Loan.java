package com.aspire.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.chain.impl.ContextBase;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {
    private Long id;
    private double amount;
    private int term;
    private Status status;
    private List<Repayment> repayments;

    public Loan(double amount, int term){
        this.amount = amount;
        this.term = term;
    }

    // Getters and setters
}
