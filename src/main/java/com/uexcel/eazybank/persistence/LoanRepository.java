package com.uexcel.eazybank.persistence;

import com.uexcel.eazybank.model.Loans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loans,Long> {
    List<Loans> findByAccounts_AccountNumber(Long accountNumber);
}
