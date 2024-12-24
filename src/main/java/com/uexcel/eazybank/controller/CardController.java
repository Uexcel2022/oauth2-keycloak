package com.uexcel.eazybank.controller;

import com.uexcel.eazybank.dto.CardsDto;
import com.uexcel.eazybank.service.ICardService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
public class CardController {
    private final ICardService cardService;
    @GetMapping("/myCards")
    public ResponseEntity<CardsDto> getCardDetails(@RequestParam String cardNumber) {
        CardsDto cRD = cardService.fetchCard(cardNumber);
        return ResponseEntity.ok(cRD);
    }
}
