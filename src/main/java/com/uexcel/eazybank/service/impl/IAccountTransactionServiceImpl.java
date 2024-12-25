package com.uexcel.eazybank.service.impl;

import com.uexcel.eazybank.dto.AccountTransactionDto;
import com.uexcel.eazybank.dto.ResponseDto;
import com.uexcel.eazybank.exceptionhandling.AppExceptionHandler;
import com.uexcel.eazybank.mapper.TransactionMapper;
import com.uexcel.eazybank.model.AccountTransactions;
import com.uexcel.eazybank.model.Accounts;
import com.uexcel.eazybank.persistence.AccountTransactionRepository;
import com.uexcel.eazybank.persistence.AccountsRepository;
import com.uexcel.eazybank.service.IAccountTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class IAccountTransactionServiceImpl implements IAccountTransactionService {
    private final AccountTransactionRepository aTRepository;
    private final TransactionMapper transMapper;
    private final AccountsRepository accountsRepository;


    @Override
    public List<AccountTransactions> getAccountTransaction(Long accountNumber) {
        return aTRepository.findByAccounts_AccountNumber(accountNumber);
    }

    @Override
    public ResponseDto addTransaction(AccountTransactionDto transactionDto) {
        List<Accounts> accounts = accountsRepository
                .findByAccountNumber(transactionDto.getAccountNumber());
        Accounts account = accounts.stream()
                .filter(t->t.getAccountNumber().equals(transactionDto.getAccountNumber()))
                .findFirst().get();
        AccountTransactions accountTransaction =
                transMapper.toAccountTransactions(transactionDto,new AccountTransactions());
        accountTransaction.setAccounts(account);
      AccountTransactions tr =  aTRepository.save(accountTransaction);

        if(tr.getTransactionId()== null){
            throw new AppExceptionHandler(
                    HttpStatus.EXPECTATION_FAILED.value(), "Fail to create transaction");
        }
        return new ResponseDto(HttpStatus.CREATED.value(),
                HttpStatus.CREATED,"Transaction created successfully");
    }
}
