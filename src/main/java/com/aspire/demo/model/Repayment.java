package com.aspire.demo.model;

import lombok.Data;

import java.util.Date;
@Data
public class Repayment {
    private Long id;
    private double amount;
    private String status;
    private Date dueDate;
    private Long loanId;

    // Getters and setters
}
