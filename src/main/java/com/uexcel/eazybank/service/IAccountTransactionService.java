package com.uexcel.eazybank.service;

import com.uexcel.eazybank.dto.AccountTransactionDto;
import com.uexcel.eazybank.dto.ResponseDto;
import com.uexcel.eazybank.model.AccountTransactions;

import java.util.List;

public interface IAccountTransactionService {
    List<AccountTransactions> getAccountTransaction(Long accountNumber);
    ResponseDto addTransaction(AccountTransactionDto transactionDto);
}
