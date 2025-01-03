package com.uexcel.eazybank.controller;

import com.uexcel.eazybank.dto.AccountsDto;
import com.uexcel.eazybank.dto.CustomerResponseDto;
import com.uexcel.eazybank.dto.ResponseDto;
import com.uexcel.eazybank.exceptionhandling.AppExceptionHandler;
import com.uexcel.eazybank.mapper.CustomerMapper;
import com.uexcel.eazybank.model.Customer;
import com.uexcel.eazybank.persistence.CustomerRepository;
import com.uexcel.eazybank.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
    private final  IAccountsService accountsService;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    @GetMapping("/myAccounts")
    public ResponseEntity<AccountsDto> getAccountDetails(@RequestParam(required = false) Long accountNumber,
                                                         @RequestParam(required = false) String email) {
        AccountsDto accountsDto;
        if (email != null){
           Customer customer = customerRepository.findByEmail(email)
                    .orElseThrow(()->new AppExceptionHandler(HttpStatus.NOT_FOUND.value(),
                            "Customer not found with mobile number " + email));
            accountsDto =accountsService
                    .fetchAccountsByCustomerIdOrAccountNumber(customer.getId(), null).getFirst();
            accountsDto.setCustomer(customerMapper.toDto(new CustomerResponseDto(),customer));
        } else {
            accountsDto = accountsService
                    .fetchAccountsByCustomerIdOrAccountNumber(null, accountNumber).getFirst();
        }
        return ResponseEntity.ok(accountsDto);
    }

    @PostMapping("create-account")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody AccountsDto accountsDto) {
        ResponseDto responseDto = accountsService.createAccount(accountsDto);
        return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
    }
}
