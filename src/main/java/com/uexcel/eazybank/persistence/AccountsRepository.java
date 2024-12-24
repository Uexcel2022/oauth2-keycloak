package com.uexcel.eazybank.persistence;

import com.uexcel.eazybank.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts,Long> {
    List<Accounts> findByAccountNumber(Long accountNumber);
    List<Accounts> findByCustomerId(Long customerId);
}
