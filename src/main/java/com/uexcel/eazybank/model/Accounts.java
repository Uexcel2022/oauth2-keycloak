package com.uexcel.eazybank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter @Setter @ToString
public class Accounts {
    @Id
    private Long accountNumber;
    private String accountType;
    private String branchAddress;
    private LocalDate createDt;
    @ManyToOne(optional = false,fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Customer customer;
}
