package com.uexcel.eazybank.exceptionhandling;

import com.uexcel.eazybank.dto.ErrorResponseDto;
import com.uexcel.eazybank.service.ICustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders
            headers, HttpStatusCode status, WebRequest request) {
          Map<String, String> errorMap = new LinkedHashMap<>();
          List<FieldError> fieldErrors = ex.getFieldErrors();
          for (FieldError fieldError : fieldErrors) {
              errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
          }

        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppExceptionHandler.class)
    public ResponseEntity<ErrorResponseDto>
    handleAppExceptionHandler(AppExceptionHandler e, WebRequest request) {
       return ResponseEntity.status(e.getStatus()).body(
                new ErrorResponseDto(
                        ICustomerService.getTime(),e.getStatus(),
                        HttpStatus.resolve(e.getStatus()), e.getMessage(),
                                request.getDescription(false)
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto>
    handleInternalServerError(Exception e, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(
                new ErrorResponseDto(
                        ICustomerService.getTime(),HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),
                        request.getDescription(false)
                )
        );
    }
}
