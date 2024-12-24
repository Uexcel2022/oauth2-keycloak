package com.uexcel.eazybank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
@Getter @Setter @ToString
public class LoanDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String loanType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate startDt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double totalLoan;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double amountPaid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  Double outstandingAmount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long accountNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate createDt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
}
