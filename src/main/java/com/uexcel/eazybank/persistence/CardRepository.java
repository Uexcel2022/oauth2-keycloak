package com.uexcel.eazybank.persistence;

import com.uexcel.eazybank.model.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Cards, Long> {
    Cards findByCardNumber(String cardNumber);
    List<Cards> findByAccounts_AccountNumber(Long accountNumber);
}
