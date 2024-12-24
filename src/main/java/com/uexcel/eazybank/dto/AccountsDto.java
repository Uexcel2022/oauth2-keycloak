package com.uexcel.eazybank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
@Getter @Setter @ToString
public class AccountsDto {
    private Long accountNumber;
    private String accountType;
    private String branchAddress;
    private LocalDate createDt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CustomerDto customer;

}
