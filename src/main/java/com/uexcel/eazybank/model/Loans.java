package com.uexcel.eazybank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
@Entity
@Getter @Setter @ToString
public class Loans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanNumber;
    private String loanType;
    private LocalDate startDt;
    private Double totalLoan;
    private Double amountPaid;
    private  Double outstandingAmount;
    private LocalDate createDt;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, optional = false)
    private Accounts accounts;
}
