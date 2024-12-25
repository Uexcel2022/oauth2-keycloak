package com.uexcel.eazybank.mapper;

import com.uexcel.eazybank.dto.AccountTransactionDto;
import com.uexcel.eazybank.model.AccountTransactions;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public AccountTransactions toAccountTransactions(AccountTransactionDto
                                                             transactionDto, AccountTransactions transaction) {
        transaction.setTransactionAmt(transactionDto.getTransactionAmt());
        transaction.setTransactionType(transactionDto.getTransactionType());
        transaction.setTransactionDt(transactionDto.getTransactionDt());
        transaction.setTransactionSummary(transactionDto.getTransactionSummary());
        transaction.setClosingBalance(transactionDto.getClosingBalance());
        return transaction;

    }
}
