package com.uexcel.eazybank.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CustomerPasswordDto {
    private String pwd;
    private String confirmPwd;
}
