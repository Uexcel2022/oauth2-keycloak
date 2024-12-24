package com.uexcel.eazybank.controller;

import com.uexcel.eazybank.dto.AccountsDto;
import com.uexcel.eazybank.dto.ResponseDto;
import com.uexcel.eazybank.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
    private final  IAccountsService accountsService;
    @GetMapping("/myAccounts")
    public ResponseEntity<AccountsDto> getAccountDetails(@RequestParam Long accountNumber) {
       AccountsDto accountsDto = accountsService
               .fetchAccountsByCustomerIdOrAccountNumber(null,accountNumber).get(0);
        return ResponseEntity.ok(accountsDto);
    }

    @PostMapping("create-account")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody AccountsDto accountsDto) {
        ResponseDto responseDto = accountsService.createAccount(accountsDto);
        return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
    }
}
