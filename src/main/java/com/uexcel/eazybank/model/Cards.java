package com.uexcel.eazybank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
@Entity
@Getter @Setter @ToString
public class Cards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardNumber;
    private String cardType;
    private Double totalLimit;
    private Double amountUsed;
    private Double availableAmount;
    private LocalDate createDt;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = false)
    private Accounts accounts;
}
