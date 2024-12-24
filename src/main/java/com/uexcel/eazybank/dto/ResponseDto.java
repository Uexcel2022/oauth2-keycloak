package com.uexcel.eazybank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ResponseDto {
    private int status;
    private HttpStatus description;
    private String message;

}
