package com.uexcel.eazybank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
@Getter @Setter @ToString
public class CardsDto{
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cardNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cardType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double totalLimit;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double amountUsed;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double availableAmount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long accountNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate createDt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String massage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CustomerDto customer;
}
