package com.uexcel.eazybank.service;

import com.uexcel.eazybank.dto.LoanDto;
import com.uexcel.eazybank.dto.ResponseDto;

import java.util.List;

public interface ILoanService {
    List<LoanDto> fetchLoanByAccountNumber(Long accountNumber);
    ResponseDto addLoan(LoanDto loanDto);
    ResponseDto updateLoan(LoanDto loanDto);
}
