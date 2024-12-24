package com.uexcel.eazybank.mapper;

import com.uexcel.eazybank.dto.CardsDto;
import com.uexcel.eazybank.model.Cards;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter @Setter @ToString
@Component
public class CardsMapper {
    public CardsDto toDto(Cards cards, CardsDto cardsDto) {
        cardsDto.setId(cards.getId());
        cardsDto.setCardNumber(cards.getCardNumber());
        cardsDto.setCardType(cards.getCardType());
        cardsDto.setAmountUsed(cards.getAmountUsed());
        cardsDto.setTotalLimit(cards.getTotalLimit());
        cardsDto.setAvailableAmount(cards.getAvailableAmount());
        cardsDto.setCreateDt(cards.getCreateDt());
        cardsDto.setAccountNumber(cards.getAccounts().getAccountNumber());
        return cardsDto;
    }
}
