package com.uexcel.eazybank.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
@Getter @Setter @ToString
public class CustomerDto {
    private Long id;
    private String name;
    private String mobileNumber;
    private String email;
    private String role;
    private LocalDate createDt;
}
