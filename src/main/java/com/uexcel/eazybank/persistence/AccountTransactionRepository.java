package com.uexcel.eazybank.persistence;

import com.uexcel.eazybank.model.AccountTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountTransactionRepository
        extends JpaRepository<AccountTransactions,String> {
    List<AccountTransactions> findByAccounts_AccountNumber(Long accountNumber);
}
