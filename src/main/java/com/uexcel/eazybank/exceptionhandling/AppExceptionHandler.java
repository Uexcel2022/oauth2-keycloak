package com.uexcel.eazybank.exceptionhandling;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
@AllArgsConstructor @Getter
public class AppExceptionHandler extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    private int status;
    private String message;
}
