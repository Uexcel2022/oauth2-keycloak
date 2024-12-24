package com.uexcel.eazybank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
@Getter @ToString
@AllArgsConstructor
public class ErrorResponseDto {
    private String timestamp;
    private int status;
    private HttpStatus description;
    private String message;
    private String apiPath;
}
