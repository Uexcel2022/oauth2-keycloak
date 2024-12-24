package com.uexcel.eazybank.service;

import com.uexcel.eazybank.dto.CardsDto;

import java.util.List;

public interface ICardService {
    List<CardsDto> fetchCardsByAccountNumber(Long accountNumber);
    CardsDto fetchCard(String cardNumber);
}
