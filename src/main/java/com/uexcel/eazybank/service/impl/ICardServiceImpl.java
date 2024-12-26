package com.uexcel.eazybank.service.impl;

import com.uexcel.eazybank.dto.CardsDto;
import com.uexcel.eazybank.dto.CustomerDto;
import com.uexcel.eazybank.exceptionhandling.AppExceptionHandler;
import com.uexcel.eazybank.mapper.CardsMapper;
import com.uexcel.eazybank.mapper.CustomerMapper;
import com.uexcel.eazybank.model.Cards;
import com.uexcel.eazybank.model.Customer;
import com.uexcel.eazybank.persistence.CardRepository;
import com.uexcel.eazybank.persistence.CustomerRepository;
import com.uexcel.eazybank.service.ICardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ICardServiceImpl implements ICardService {
    private final CardRepository cardRepository;
    private final CardsMapper cardsMapper;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    /**
     * @param accountNumber
     * @return
     */
    @Override
    public List<CardsDto> fetchCardsByAccountNumber(Long accountNumber) {
        List<Cards> cards = cardRepository.findByAccounts_AccountNumber(accountNumber);
        List<CardsDto> cardDtoList = new ArrayList<>();
        if (cards.isEmpty()) {
            CardsDto cardsDto = new CardsDto();
            cardsDto.setStatus(HttpStatus.NOT_FOUND.value());
            cardsDto.setMassage("There is no card associated with accountNumber id " + accountNumber);
            cardDtoList.add(cardsDto);
            return cardDtoList;
        }

        cards.forEach( card ->
                cardDtoList.add(cardsMapper.toDto(card, new CardsDto()))
        );
        return cardDtoList;
    }

    /**
     * @param cardNumber
     * @return
     */
    @Override
    public CardsDto fetchCard(String cardNumber) {
        Cards card = cardRepository.findByCardNumber(cardNumber);
        if (card == null) {
            throw new AppExceptionHandler(HttpStatus.NOT_FOUND.value(),
                    String.format("Card not found for the given input data cardNumber: %s ",cardNumber));
        }
        CardsDto cardDto = cardsMapper.toDto(card, new CardsDto());
        Customer customer = card.getAccounts().getCustomer();
        CustomerDto cDto = new CustomerDto();
        cDto.setId(customer.getId());
        cDto.setName(customer.getName());
        cDto.setEmail(customer.getEmail());
        cDto.setMobileNumber(customer.getMobileNumber());
        cDto.setCreateDt(customer.getCreateDt());
        cardDto.setCustomer(cDto);
        return cardDto;
    }
}
