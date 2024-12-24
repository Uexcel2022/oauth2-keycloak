package com.uexcel.eazybank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateCustomerDto extends CustomerPasswordDto {
    private String name;
    private String mobileNumber;
    private String email;
    private String role;
}
