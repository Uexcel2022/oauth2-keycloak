package com.uexcel.eazybank.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
public class BalanceController {
    @GetMapping("/myBalance")
    public ResponseEntity<String> getBalanceDetails() {
        return ResponseEntity.ok("Account balance info from DD");
    }
}
