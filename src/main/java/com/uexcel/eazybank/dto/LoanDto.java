package com.uexcel.eazybank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
@Getter @Setter @ToString
public class LoanDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull
    @NotEmpty
    private String loanType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull
    private LocalDate startDt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Positive(message = "Must be greater than zero.")
    private Double totalLoan;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Positive(message = "Must be greater than zero.")
    private Double amountPaid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @PositiveOrZero(message = "Must be zero or greater.")
    private  Double outstandingAmount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Min(1000000000)
    private Long accountNumber;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDate createDt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
}
