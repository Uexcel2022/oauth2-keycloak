package com.uexcel.eazybank.mapper;

import com.uexcel.eazybank.dto.AccountsDto;
import com.uexcel.eazybank.model.Accounts;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;

@Component
public class AccountsMapper {
    public AccountsDto mapToDto(Accounts accounts, AccountsDto accountsDto) {
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        accountsDto.setCreateDt(accounts.getCreateDt());
        return accountsDto;
    }

    public Accounts toAccount(Accounts accounts, AccountsDto accountDto) {
        Long accountNumber = 1000000000 + new Random().nextLong(1000000000);
        accounts.setAccountNumber(accountNumber);
        accounts.setAccountType(accountDto.getAccountType());
        accounts.setBranchAddress(accountDto.getBranchAddress());
        accounts.setCreateDt(LocalDate.now());
        return accounts;

    }
}
