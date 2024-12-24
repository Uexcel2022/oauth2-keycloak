package com.uexcel.eazybank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
public class CustomerResponseDto extends CustomerDto {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<AccountsDto> accounts = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<LoanDto> loans = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CardsDto> cards = new ArrayList<>();
}
