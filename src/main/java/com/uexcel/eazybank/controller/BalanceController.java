package com.uexcel.eazybank.controller;

import com.uexcel.eazybank.dto.AccountTransactionDto;
import com.uexcel.eazybank.dto.ResponseDto;
import com.uexcel.eazybank.model.AccountTransactions;
import com.uexcel.eazybank.service.IAccountTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
public class BalanceController {
    private final IAccountTransactionService iATService;
    @GetMapping("/myBalance")
    public ResponseEntity<List<AccountTransactions>> getBalanceDetails(@RequestParam Long accountNumber) {
        List<AccountTransactions> tr = iATService.getAccountTransaction(accountNumber);
        return ResponseEntity.ok(tr);
    }
    @PostMapping("/add-transaction")
    public ResponseEntity<ResponseDto> addTransaction(
            @RequestBody AccountTransactionDto transactionDto) {
        ResponseDto responseDto = iATService.addTransaction(transactionDto);
        return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
    }
}
