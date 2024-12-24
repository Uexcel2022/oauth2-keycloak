package com.uexcel.eazybank.service;

import com.uexcel.eazybank.dto.LoanDto;

import java.util.List;

public interface ILoanService {
    List<LoanDto> fetchLoanByAccountNumber(Long accountNumber);
}
