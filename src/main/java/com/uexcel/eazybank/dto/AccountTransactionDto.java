package com.uexcel.eazybank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccountTransactionDto {
    private Long accountNumber;
    private String transactionDt;
    private String transactionSummary;
    private String transactionType;
    private String transactionAmt;
    private String closingBalance;
}
